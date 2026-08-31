package com.rupesh.blog.idam.controller;


import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.rupesh.blog.idam.dto.FullIdamReconRequest;
import com.rupesh.blog.idam.dto.IdamRequest;
import com.rupesh.blog.idam.dto.IdamResponse;
import com.rupesh.blog.idam.services.IdamService;
import com.rupesh.blog.idam.util.IdamUtil;

@RestController
public class IdamRestController {

    @Autowired
    private IdamService idamService;

    @Autowired
    private IdamUtil idamUtil;


    /*
     * ============================================================
     * IMPORTANT KEYS
     * ============================================================
     *
     * EIS PRIVATE KEY
     * ----------------
     * Used for:
     * 1. Decrypting Channel's AccessToken
     * 2. Signing EIS response
     *
     * CHANNEL PUBLIC KEY
     * ------------------
     * Used for:
     * 1. Verifying Channel DIGI_SIGN
     * 2. Encrypting response AccessToken
     *
     * DO NOT generate these keys for every request.
     *
     * Load them from your keystore/configuration.
     * ============================================================
     */

    private PrivateKey eisPrivateKey;

    private PublicKey channelPublicKey;


    /*
     * ============================================================
     * Set your keys during application initialization.
     *
     * Replace these methods with your actual keystore loading.
     * ============================================================
     */

    public void setEisPrivateKey(PrivateKey eisPrivateKey) {
        this.eisPrivateKey = eisPrivateKey;
    }

    public void setChannelPublicKey(PublicKey channelPublicKey) {
        this.channelPublicKey = channelPublicKey;
    }


    /*
     * ============================================================
     * ALL DETAILS API
     * ============================================================
     */

    @RequestMapping(
            value = "/allDetails",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public IdamResponse getDetailsFull(
            @RequestBody String idamRequest,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {


        /*
         * ========================================================
         * 0. INITIAL VALIDATION
         * ========================================================
         */

        if (idamRequest == null || idamRequest.trim().isEmpty()) {

            throw new SecurityException(
                    "Request body is empty"
            );
        }

        if (eisPrivateKey == null) {

            throw new SecurityException(
                    "EIS private key is not configured"
            );
        }

        if (channelPublicKey == null) {

            throw new SecurityException(
                    "Channel public key is not configured"
            );
        }


        /*
         * ========================================================
         * 1. CONVERT HTTP JSON INTO IdamRequest
         * ========================================================
         */

        Gson gson = new Gson();

        IdamRequest requestObjectPayload =
                gson.fromJson(
                        idamRequest,
                        IdamRequest.class
                );


        /*
         * Validate request object
         */

        if (requestObjectPayload == null) {

            throw new SecurityException(
                    "Invalid request JSON"
            );
        }


        /*
         * ========================================================
         * 2. GET ACCESS TOKEN FROM HTTP HEADER
         * ========================================================
         *
         * Channel sends:
         *
         * AccessToken:
         * <RSA encrypted AES key>
         *
         */

        String accessToken =
                request.getHeader("AccessToken");


        if (accessToken == null ||
                accessToken.trim().isEmpty()) {

            throw new SecurityException(
                    "AccessToken header is missing"
            );
        }


        /*
         * ========================================================
         * 3. RSA DECRYPT ACCESS TOKEN
         * ========================================================
         *
         * Channel encrypted the AES key using:
         *
         * EIS PUBLIC KEY
         *
         * Therefore EIS decrypts it using:
         *
         * EIS PRIVATE KEY
         *
         */

        String secretKey =
                idamUtil.decryptSHA256Message(
                        accessToken,
                        eisPrivateKey
                );


        /*
         * ========================================================
         * 4. VALIDATE AES SECRET KEY
         * ========================================================
         *
         * Specification:
         *
         * AES-256
         * 32 characters / 32 bytes
         *
         */

        if (secretKey == null ||
                secretKey.length() != 32) {

            throw new SecurityException(
                    "Invalid AES secret key. " +
                    "Expected exactly 32 characters."
            );
        }


        /*
         * Ensure key is 32 bytes.
         *
         * This is important because UTF-8 characters can occupy
         * more than one byte.
         */

        if (secretKey.getBytes(
                StandardCharsets.UTF_8
        ).length != 32) {

            throw new SecurityException(
                    "AES secret key must contain " +
                    "exactly 32 UTF-8 bytes."
            );
        }


        /*
         * ========================================================
         * 5. GET ENCRYPTED REQUEST
         * ========================================================
         *
         * JSON:
         *
         * {
         *   "REQUEST_REFERENCE_NUMBER": "...",
         *   "REQUEST": "...",
         *   "DIGI_SIGN": "..."
         * }
         *
         */

        String encryptedRequest =
                requestObjectPayload.getREQUEST();


        if (encryptedRequest == null ||
                encryptedRequest.trim().isEmpty()) {

            throw new SecurityException(
                    "REQUEST field is missing"
            );
        }


        /*
         * ========================================================
         * 6. AES-256-GCM DECRYPT REQUEST
         * ========================================================
         *
         * Specification:
         *
         * AES-256-GCM
         * NoPadding
         * IV = first 12 bytes of secret key
         * GCM Tag = 16 bytes
         *
         */

        String plainRequest =
                idamUtil.decrypt_GCM(
                        encryptedRequest,
                        secretKey
                );


        if (plainRequest == null ||
                plainRequest.trim().isEmpty()) {

            throw new SecurityException(
                    "Unable to decrypt REQUEST"
            );
        }


        /*
         * ========================================================
         * 7. VERIFY DIGITAL SIGNATURE
         * ========================================================
         *
         * VERY IMPORTANT:
         *
         * The specification says Channel signs the
         * PLAIN TEXT request.
         *
         * Therefore we MUST verify:
         *
         * plainRequest
         *
         * NOT:
         *
         * encryptedRequest
         *
         *
         * Channel Private Key:
         *       |
         *       | SHA256withRSA
         *       v
         * DIGI_SIGN
         *
         * EIS verifies using:
         *
         * Channel Public Key
         *
         */

        String digiSign =
                requestObjectPayload.getDIGI_SIGN();


        if (digiSign == null ||
                digiSign.trim().isEmpty()) {

            throw new SecurityException(
                    "DIGI_SIGN field is missing"
            );
        }


        boolean signatureValid =
                idamUtil.signVerify(
                        plainRequest,
                        digiSign,
                        channelPublicKey
                );


        /*
         * ========================================================
         * 8. STOP IF SIGNATURE IS INVALID
         * ========================================================
         */

        if (!signatureValid) {

            throw new SecurityException(
                    "DIGI_SIGN verification failed"
            );
        }


        /*
         * ========================================================
         * 9. CONVERT DECRYPTED REQUEST INTO BUSINESS OBJECT
         * ========================================================
         *
         * Replace IdamBusinessRequest.class with your actual
         * request DTO class.
         *
         * Example:
         *
         * FullIdamReconRequest businessRequest =
         *     gson.fromJson(
         *         plainRequest,
         *         FullIdamReconRequest.class
         *     );
         *
         */

        // FullIdamReconRequest businessRequest =
        //         gson.fromJson(
        //                 plainRequest,
        //                 FullIdamReconRequest.class
        //         );


        /*
         * ========================================================
         * 10. BUSINESS LOGIC
         * ========================================================
         */

        FullIdamReconRequest payload =
                idamService.getallDetails();


        /*
         * ========================================================
         * 11. CONVERT RESPONSE TO JSON
         * ========================================================
         *
         * Your old code had:
         *
         * String plainResponse = "{}";
         *
         * That should normally be:
         *
         * gson.toJson(payload)
         *
         */

        String plainResponse =
                gson.toJson(payload);


        /*
         * ========================================================
         * 12. AES ENCRYPT RESPONSE
         * ========================================================
         *
         * IMPORTANT:
         *
         * The specification says the response uses the same
         * AES secret key obtained from AccessToken.
         *
         * Therefore:
         *
         * DO NOT generate another AES key here.
         *
         */

        String encryptedResponse =
                idamUtil.encrypt_GCM(
                        plainResponse,
                        secretKey
                );


        /*
         * ========================================================
         * 13. SIGN PLAIN RESPONSE
         * ========================================================
         *
         * EIS signs the PLAIN response using:
         *
         * EIS PRIVATE KEY
         *
         * Channel verifies using:
         *
         * EIS PUBLIC KEY
         *
         */

        String responseDigiSign =
                idamUtil.signRequest(
                        plainResponse,
                        eisPrivateKey
                );


        /*
         * ========================================================
         * 14. ENCRYPT AES KEY FOR CHANNEL
         * ========================================================
         *
         * EIS encrypts the AES key using:
         *
         * CHANNEL PUBLIC KEY
         *
         * Channel decrypts using:
         *
         * CHANNEL PRIVATE KEY
         *
         */

        String responseAccessToken =
                idamUtil.encryptSHA256Message(
                        secretKey,
                        channelPublicKey
                );


        /*
         * ========================================================
         * 15. PUT ENCRYPTED AES KEY IN HTTP RESPONSE HEADER
         * ========================================================
         */

        response.setHeader(
                "AccessToken",
                responseAccessToken
        );


        /*
         * ========================================================
         * 16. CREATE RESPONSE OBJECT
         * ========================================================
         */

        IdamResponse idamResponse =
                new IdamResponse();


        /*
         * Encrypted response
         */

        idamResponse.setResponse(
                encryptedResponse
        );


        /*
         * Same request reference number
         */

        idamResponse.setRequestReferenceNumber(
                requestObjectPayload
                        .getREQUEST_REFERENCE_NUMBER()
        );


        /*
         * Response date
         */

        idamResponse.setResponseDate(
                LocalDateTime
                        .now()
                        .format(
                                DateTimeFormatter.ofPattern(
                                        "dd-MM-yyyy HH:mm:ss"
                                )
                        )
        );


        /*
         * Digital signature
         */

        idamResponse.setDigiSign(
                responseDigiSign
        );


        /*
         * ========================================================
         * 17. RETURN RESPONSE
         * ========================================================
         */

        return idamResponse;
    }
} 