package ie.shanequaid.scc.service

import ie.shanequaid.scc.exceptions.S3BucketException
import ie.shanequaid.scc.persistence.model.Image
import ie.shanequaid.scc.persistence.model.dto.ImageDTO
import ie.shanequaid.scc.persistence.model.dto.toDto
import ie.shanequaid.scc.persistence.repository.IImageRepository
import ie.shanequaid.scc.spring.properties.SCCBucketProperties
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException

@Service
class ImageService(
    private val s3BucketService: S3BucketService,
    private val repository: IImageRepository,
    @Qualifier("SCCBucketProperties") private val properties: SCCBucketProperties,
) {
    private val logger = KotlinLogging.logger {}

    fun saveImage(file: MultipartFile): ImageDTO {

        val metadata: MutableMap<String, String> = HashMap()
        metadata["Content-Type"] = file.contentType!!
        metadata["Content-Length"] = file.size.toString()
        //Save Image in S3 and then save Todo in the database
        val s3BucketFolder = "images"
        val path = java.lang.String.format("%s/%s", properties.name, s3BucketFolder)
        val fileName = String.format("%s", file.originalFilename).replace(" ", "")
        try {
            s3BucketService.upload(path, fileName, metadata, file.inputStream)
        } catch (e: IOException) {
            logger.error(e) { "Issue writing to s3 $path" }
            throw S3BucketException("Failed to upload file")
        }
        val url = properties.url + "/" + s3BucketFolder + "/" + fileName;
        val todo = Image(id = null, path = url, fileName = fileName)

        val save: Image = repository.save(todo)
        return save.toDto()
    }
}