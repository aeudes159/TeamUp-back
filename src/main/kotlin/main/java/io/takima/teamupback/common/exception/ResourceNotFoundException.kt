package main.java.io.takima.teamupback.common.exception

class ResourceNotFoundException(
    message: String
) : RuntimeException(message) {
    constructor(resourceName: String, fieldName: String, fieldValue: Any) : this(
        "$resourceName not found with $fieldName: '$fieldValue'"
    )
}
