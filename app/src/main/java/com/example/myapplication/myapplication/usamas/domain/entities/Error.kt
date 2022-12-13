package com.example.myapplication.myapplication.usamas.domain.entities

import android.os.Parcelable
import androidx.compose.runtime.Stable
import kotlinx.parcelize.Parcelize

@Stable
@Parcelize
data class RetrofitException(
    override val message: String,
    val statusCode: StatusCode,
    val error: ServerError,
    val throwable: Throwable? = null
) : Throwable(), Parcelable

@Stable
sealed interface ServerError : Parcelable {
    @Parcelize
    @Stable
    data class BadRequest(val code: BadRequestCode) : ServerError

    @Parcelize
    @Stable
    object InternalServerError : ServerError

    @Parcelize
    @Stable
    object Conflict : ServerError

    @Parcelize
    @Stable
    object UnprocessableEntity : ServerError

    @Parcelize
    @Stable
    object TooManyRequests : ServerError

    @Parcelize
    @Stable
    object ServiceUnavailable : ServerError

    @Parcelize
    @Stable
    object Unknown : ServerError

    @Parcelize
    @Stable
    data class Unauthorized(val code: UnauthorizedCode) : ServerError

    @Parcelize
    @Stable
    data class Forbidden(val code: ForbiddenCode) : ServerError
}

@Parcelize
enum class BadRequestCode(val code: Int) : Parcelable {
    AccountAlreadyExist(411),
    Unknown(-1);

    companion object {
        fun fromCode(code: Int) =
            values().associateBy(BadRequestCode::code)[code]
                ?: Unknown
    }
}

@Parcelize
enum class UnauthorizedCode(val code: Int) : Parcelable {
    Unknown(-1);

    companion object {
        fun fromCode(code: Int) =
            values().associateBy(UnauthorizedCode::code)[code]
                ?: Unknown
    }
}

@Parcelize
enum class ForbiddenCode(val code: Int) : Parcelable {
    Unknown(-1);

    companion object {
        fun fromCode(code: Int) =
            values().associateBy(ForbiddenCode::code)[code]
                ?: Unknown
    }
}

@Stable
@Parcelize
enum class StatusCode(val code: Int) : Parcelable {
    Continue(100),
    SwitchingProtocols(101),
    Processing(102),

    OK(200),
    Created(201),
    Accepted(202),
    NonAuthoritativeInformation(203),
    NoContent(204),
    ResetContent(205),
    PartialContent(206),
    MultiStatus(207),
    AlreadyReported(208),
    IMUsed(226),

    MultipleChoices(300),
    MovedPermanently(301),
    Found(302),
    SeeOther(303),
    NotModified(304),
    UseProxy(305),
    TemporaryRedirect(307),
    PermanentRedirect(308),

    BadRequest(400),
    Unauthorized(401),
    PaymentRequired(402),
    Forbidden(403),
    NotFound(404),
    MethodNotAllowed(405),
    NotAcceptable(406),
    ProxyAuthenticationRequired(407),
    RequestTimeout(408),
    Conflict(409),
    Gone(410),
    LengthRequired(411),
    PreconditionFailed(412),
    PayloadTooLarge(413),
    UriTooLong(414),
    UnsupportedMediaType(415),
    RangeNotSatisfiable(416),
    ExpectationFailed(417),
    IAmATeapot(418),
    MisdirectedRequest(421),
    UnprocessableEntity(422),
    Locked(423),
    FailedDependency(424),
    UpgradeRequired(426),
    PreconditionRequired(428),
    TooManyRequests(429),
    RequestHeaderFieldsTooLarge(431),
    UnavailableForLegalReasons(451),

    InternalServerError(500),
    NotImplemented(501),
    BadGateway(502),
    ServiceUnavailable(503),
    GatewayTimeout(504),
    HttpVersionNotSupported(505),
    VariantAlsoNegotiates(506),
    InsufficientStorage(507),
    LoopDetected(508),
    NotExtended(510),
    NetworkAuthenticationRequired(511),

    Unknown(0);

    companion object {
        fun fromCode(code: Int) =
            values().associateBy(StatusCode::code)[code] ?: Unknown
    }
}

fun Throwable.asRetrofitException() =
    RetrofitException(
        localizedMessage ?: message ?: "",
        StatusCode.Unknown,
        ServerError.Unknown,
        this
    )