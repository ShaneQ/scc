package ie.shanequaid.scc.service

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService
import com.amazonaws.services.simpleemail.model.Body
import com.amazonaws.services.simpleemail.model.Content
import com.amazonaws.services.simpleemail.model.Destination
import com.amazonaws.services.simpleemail.model.Message
import com.amazonaws.services.simpleemail.model.SendEmailRequest
import ie.shanequaid.scc.persistence.model.BookingRequest
import ie.shanequaid.scc.spring.properties.SCCEmailProperties
import mu.KotlinLogging
import org.apache.commons.codec.CharEncoding
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.thymeleaf.context.Context
import org.thymeleaf.spring5.SpringTemplateEngine
import java.time.format.DateTimeFormatter
import java.util.UUID

@Service
class EmailService(
    private val client: AmazonSimpleEmailService,
    @Qualifier("SCCEmailProperties") private val properties: SCCEmailProperties,
    private val templateEngine: SpringTemplateEngine
) {
    private val logger = KotlinLogging.logger {}

    fun sendEmailActivatedAdmin(fullName: String, email: String) {
        val props: MutableMap<String, Any> = HashMap()
        props["name"] = fullName
        props["email"] = email
        val html = getTemplateHtml(props, "admin/new-user-register")
        val emailSubject = "SCC User has registered"

        try {
            val sendEmailRequest = SendEmailRequest()
                .withDestination(
                    Destination().withToAddresses(properties.adminEmail)
                )
                .withMessage(
                    Message()
                        .withBody(
                            Body().withHtml(
                                Content().withCharset(CharEncoding.UTF_8).withData(html)
                            )
                        )
                        .withSubject(Content().withCharset(CharEncoding.UTF_8).withData(emailSubject))
                )
                .withSource(properties.senderEmail)
            client.sendEmail(sendEmailRequest)
            logger.info { "Registration email sent to admin" }
        } catch (e: Exception) {
            logger.error(e) { "Failed to send admin email" }
        }
    }

    private fun getTemplateHtml(props: Map<String, Any>, template: String): String {
        val context = Context()
        context.setVariables(props)
        return templateEngine.process(template, context)
    }

    fun sendEmailActivatedUser(firstName: String, userId: UUID?, email: String?) {
        val emailSubject = "Welcome to the 2nd Closet Club"
        val props: MutableMap<String, Any> = HashMap()
        props["name"] = firstName
        val html = getTemplateHtml(props, "public/new-user-activation")
        try {
            val sendEmailRequest = SendEmailRequest()
                .withDestination(
                    Destination().withToAddresses(email)
                )
                .withMessage(
                    Message()
                        .withBody(
                            Body().withHtml(
                                Content().withCharset(CharEncoding.UTF_8).withData(html)
                            )
                        )
                        .withSubject(Content().withCharset(CharEncoding.UTF_8).withData(emailSubject))
                )
                .withSource(properties.senderEmail)
            client.sendEmail(sendEmailRequest)
            logger.info { "User Activation email sent to $userId" }
        } catch (e: Exception) {
            logger.error(e) { "Failed to send email to user $userId" }
        }
    }

    fun sendEmailBookingAdmin(bookingRequest: BookingRequest) {
        val sizeName =
            bookingRequest.product.sizes.first { it.id!! == bookingRequest.productInventory.id }.size!!.name
        val props: MutableMap<String, String> = HashMap()
        props["bookingId"] = bookingRequest.id.toString()
        props["fullName"] = bookingRequest.user.fullName
        props["productId"] = bookingRequest.product.id.toString()
        props["productName"] = bookingRequest.product.name
        props["productSize"] = sizeName
        props["bookingStartDate"] = bookingRequest.startDate.format(DateTimeFormatter.ISO_DATE)
        props["collectionLocation"] = bookingRequest.collectionPlace
        val html = getTemplateHtml(props, "admin/new-booking-request")
        val emailSubject = "New Booking Request"
        try {
            val sendEmailRequest = SendEmailRequest()
                .withDestination(
                    Destination().withToAddresses(properties.adminEmail)
                )
                .withMessage(
                    Message()
                        .withBody(
                            Body().withHtml(
                                Content().withCharset(CharEncoding.UTF_8).withData(html)
                            )
                        )
                        .withSubject(Content().withCharset(CharEncoding.UTF_8).withData(emailSubject))
                )
                .withSource(properties.senderEmail)
            client.sendEmail(sendEmailRequest)
            logger.info { "Booking request email to Admin for booking id ${bookingRequest.id}" }
        } catch (e: Exception) {
            logger.error(e) { "Failed to send email to Admin for booking id ${bookingRequest.id} " }
        }
    }
}