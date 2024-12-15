package com.photo.starsnap.auth.constant

import com.photo.starsnap.auth.dto.KeyCredential

object PasskeysData {
    const val RP_ID = "starsnap.com"
    const val RP_NAME = "starsnap"
    const val USER_ID = "WUU0ZmQzYWFmNGU0NTMyNGQwZjNlMTM0NjA3YjIxOTEyYg"
    const val USER_NAME = "user1"
    const val USER_DISPLAY_NAME = "User One"
    const val CREATE_CHALLENGE = "WUYwNDhkMWE3ZWMzYTJhNjk3MDA1OWMyNzY2YmJjN2UwZg"
    const val PUB_KEY_CRED_TYPE = "public-key"
    const val PUB_KEY_CRED_ALG = -7
    const val TIMEOUT = 60000
    val keyCredential =
        KeyCredential(
            id = "6ySmhJd6qGUMCthiqszyb4Od4U6TFn0v3DLz-1EZrNQ",
            type = "public-key",
            listOf("internal", "hybrid"),
        )
    const val AUTHENTICATOR_SELECTION_AUTHENTICATOR_ATTACHMENT = "platform"
    const val AUTHENTICATOR_SELECTION_REQUIRE_RESIDENT_KEY = false
    const val AUTHENTICATOR_SELECTION_RESIDENT_KEY = "preferred"
    const val AUTHENTICATOR_SELECTION_USER_VERIFICATION = "preferred"
    const val ATTESTATION = "none"


    val createPayload =
        """{
    "rawId": "<rawId>",
    "authenticatorAttachment": "platform",
    "type": "public-key",
    "id": "<id>",
    "response": {
        "clientDataJSON": "<clientDataJSON>",
        "attestationObject": "<attestationObject>",
        "transports": [
            "internal",
            "cable"
        ]
    },
    "clientExtensionResults": {
        "credProps": {
            "rk": false
        }
    }
}
  """.trimIndent()
}