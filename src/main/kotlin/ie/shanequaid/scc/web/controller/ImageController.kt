package ie.shanequaid.scc.web.controller

import ie.shanequaid.scc.persistence.model.dto.ImageDTO
import ie.shanequaid.scc.service.ImageService
import org.apache.http.entity.ContentType
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping(value = ["/api/admin/image"])
class ImageController(
    private val service: ImageService
) {

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun saveImage(@RequestParam("file") file: MultipartFile): ImageDTO {
        validateImage(file)
        return service.saveImage(file)
    }

    fun validateImage(file: MultipartFile){
        check(!file.isEmpty) { IllegalStateException("Cannot upload empty file") }
        check(
            listOf(
                ContentType.IMAGE_PNG.mimeType,
                ContentType.IMAGE_BMP.mimeType,
                ContentType.IMAGE_GIF.mimeType,
                ContentType.IMAGE_JPEG.mimeType
            ).contains(file.contentType)
        ) { IllegalStateException("File uploaded is not an image") }

    }
}
