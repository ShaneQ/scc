package ie.shanequaid.scc.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class ResourceNotFoundException(message: String) : RuntimeException(message)

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
class S3BucketException(message: String) : RuntimeException(message)