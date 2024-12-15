package com.photo.starsnap.main.dto

import kotlinx.serialization.Serializable

/**
 * Represents a request for creating a passkey.
 *
 * @property challenge The challenge for the passkey creation.
 * @property rp The relying party (RP) information.
 * @property user The user information.
 * @property pubKeyCredParams The list of public key credential parameters.
 * @property timeout The timeout value for the passkey creation.
 * @property attestation The attestation type for the passkey creation.
 * @property excludeCredentials The list of key credentials to exclude.
 * @property authenticatorSelection The authenticator selection criteria.
 */
@Serializable
data class CreatePasskeyRequest(
    var challenge: String,
    val rp: Rp,
    val user: User,
    val pubKeyCredParams: List<PubKeyCredParams>,
    val timeout: Long,
    val attestation: String? = null,
    val excludeCredentials: List<KeyCredential>? = null,
    val authenticatorSelection: AuthenticatorSelection,
)

/**
 * Represents relying party (RP) information.
 *
 * @property name The name of the relying party.
 * @property id The identifier of the relying party.
 * @property icon The icon URL of the relying party.
 */
@Serializable
data class Rp(
    val name: String,
    val id: String,
    val icon: String?,
)

/**
 * Represents user information.
 *
 * @property id The identifier of the user.
 * @property icon The icon URL of the user.
 * @property name The name of the user.
 * @property displayName The display name of the user.
 */
@Serializable
data class User(
    var id: String,
    var icon: String?,
    val name: String,
    val displayName: String?,
)

/**
 * Represents public key credential parameters.
 *
 * @property type The type of the public key credential.
 * @property alg The algorithm used by the public key credential.
 */
@Serializable
data class PubKeyCredParams(
    val type: String,
    val alg: Int,
)

/**
 * Represents public key credential parameters.
 *
 * @property type The type of the public key credential.
 * @property alg The algorithm used by the public key credential.
 */
@Serializable
data class AuthenticatorSelection(
    val authenticatorAttachment: String?,
    val requireResidentKey: Boolean?,
    val residentKey: String? = null,
    val userVerification: String?,
)

/**
 * Represents authenticator selection criteria.
 *
 * @property authenticatorAttachment The authenticator attachment type.
 * @property requireResidentKey Specifies whether a resident key is required.
 * @property residentKey The resident key.
 * @property userVerification The user verification method.
 */
@Serializable
data class KeyCredential(
    val id: String,
    val type: String,
    val transports: List<String>,
)