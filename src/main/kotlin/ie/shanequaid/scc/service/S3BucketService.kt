package ie.shanequaid.scc.service

import com.amazonaws.AmazonServiceException
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.util.IOUtils
import org.springframework.stereotype.Service
import java.io.IOException
import java.io.InputStream

@Service
class S3BucketService(
    private val amazonS3: AmazonS3
) {
    fun upload(
        path: String,
        fileName: String,
        metaData: Map<String, String>,
        inputStream: InputStream
    ) {
        val objectMetadata = ObjectMetadata()
        if (metaData.isNotEmpty()) {
            metaData.forEach { (key: String?, value: String?) ->
                objectMetadata.addUserMetadata(
                    key,
                    value
                )
            }
        }
        try {
            amazonS3.putObject(path, fileName, inputStream, objectMetadata)
        } catch (e: AmazonServiceException) {
            throw IllegalStateException("Failed to upload the file", e)
        }
    }

    fun download(path: String?, key: String?): ByteArray {
        return try {
            val s3Object = amazonS3.getObject(path, key)
            val objectContent = s3Object.objectContent
            IOUtils.toByteArray(objectContent)
        } catch (e: AmazonServiceException) {
            throw IllegalStateException("Failed to download the file", e)
        } catch (e: IOException) {
            throw IllegalStateException("Failed to download the file", e)
        }
    }
}