package ie.shanequaid.scc.persistence.model.dto

import ie.shanequaid.scc.persistence.model.BookingRequest
import ie.shanequaid.scc.persistence.model.BookingStatus
import ie.shanequaid.scc.persistence.model.Color
import ie.shanequaid.scc.persistence.model.Image
import ie.shanequaid.scc.persistence.model.Product
import ie.shanequaid.scc.persistence.model.ProductCategory
import ie.shanequaid.scc.persistence.model.ProductInventory
import ie.shanequaid.scc.persistence.model.ProductInventoryStatus
import ie.shanequaid.scc.persistence.model.ProductMeasurement
import ie.shanequaid.scc.persistence.model.Season
import ie.shanequaid.scc.persistence.model.Size
import ie.shanequaid.scc.persistence.model.User
import ie.shanequaid.scc.persistence.model.UserStatus
import java.time.LocalDate
import java.util.UUID

fun BookingRequestDTO.toEntity(user: User, product: Product, status: BookingStatus) = BookingRequest(
    id = id,
    startDate = startDate,
    collectionPlace = collectionPlace,
    status = status,
    productInventory = product.sizes.first { it.id == productSize.id },
    user = user,
    product = product
)

fun ProductInventoryDTO.toEntity() = ProductInventory(
    id = id,
    status = status,
    id_product = product.id,
    size = Size(id_size, "")
)

fun ProductDTO.toEntity() = Product(
    id = id,
    name = name,
    dryClean = dryClean,
    hidden = hidden,
    deleted = false,
    quickDesc = quickDesc,
    brand = brand,
    material = material,
    description = description,
    fittingInfo = fittingInfo,
    washInfo = washInfo,
    retailPrice = retailPrice.toDouble(),
    color = Color(id = color.toLong()),
    category = ProductCategory(id = productCategory.toLong()),
    season = Season(id = season.toLong()),
    measurement = measurements.toEntity(id, null),
    imgCover = imgCover.toEntity(),
    images = images.map { it.toEntity() },
    sizes = sizes.map { ProductInventory(id = it.id) }

)

fun ImageDTO.toEntity() = Image(
    id = id,
    path = url
)

fun ProductMeasurementDTO.toEntity(id: Long, product: Product?) = ProductMeasurement(
    id = id,
    chest = chest,
    hips = hips,
    waist = waist,
    length = length,
    product = product
)

fun ProductInventoryTwoDTO.toEntity(productId: Long) = ProductInventory(
    id = id, size = Size(id_size, ""), status = ProductInventoryStatus.STORED, id_product = productId
)

fun Image.toDto() = ImageDTO(
    id = id!!,
    url = path!!
)

fun UserDTO.toEntity(userId: UUID, startDate: LocalDate?, endDate: LocalDate?, membership: Number?, status: UserStatus) = User(
    id = userId,
    firstName = firstName,
    lastName = lastName,
    phone = phone,
    email = email,
    dob = dateOfBirth,
    country = country,
    city = city,
    eirCode = eirCode,
    addressLineOne = addressLineOne,
    addressLineTwo = addressLineTwo,
    startDate = startDate,
    endDate = endDate,
    membership = membership?.toInt(),
    status = status
)

fun UserDTO.toNewUserEntity(userId: UUID) = User(
    id = userId,
    firstName = firstName,
    lastName = lastName,
    phone = phone,
    email = email,
    dob = dateOfBirth,
    country = country,
    city = city,
    eirCode = eirCode,
    addressLineOne = addressLineOne,
    addressLineTwo = addressLineTwo,
    membership = membership!!.toInt(),
    status = UserStatus.REQUESTED
)

fun BookingRequest.toDto() = BookingRequestDTO(
    id = id,
    startDate = startDate,
    returnDate = startDate.plusDays(7),
    collectionPlace = collectionPlace,
    status = status,
    userId = user.id!!,
    userName = user.fullName,
    productId = product.id!!,
    productName = product.name,
    productSize = productInventory.toDtoView(),
    coverImg = product.imgCover.toDto()

)

fun ProductInventory.toDtoView() = ProductInventoryTwoDTO(
    id = id, id_size = size!!.id, status = status

)
fun ProductInventory.toDto(product: ProductDTO) = ProductInventoryDTO(
    product = product,
    status = status!!,
    id = id!!,
    id_size = size!!.id!!
)

fun Product.toDto() = ProductDTO(

    id = id!!,
    name = name,
    dryClean = dryClean,
    hidden = hidden,
    quickDesc = quickDesc,
    brand = brand,
    material = material,
    description = description,
    fittingInfo = fittingInfo,
    washInfo = washInfo,
    retailPrice = retailPrice,
    color = color.id,
    productCategory = category.id,
    season = season.id,
    measurements = measurement.toDto(),
    imgCover = imgCover.toDto(),
    images = images.map { it.toDto() },
    sizes = sizes.map { it.toDtoView() }
)

fun ProductMeasurement.toDto() = ProductMeasurementDTO(
    chest = chest,
    hips = hips,
    waist = waist,
    length = length
)

fun User.toDto() = UserDTO(

    id = id!!,
    firstName = firstName,
    lastName = lastName,
    phone = phone,
    email = email,
    dateOfBirth = dob,
    country = country,
    city = city,
    eirCode = eirCode,
    addressLineOne = addressLineOne,
    addressLineTwo = addressLineTwo,
    startDate = startDate,
    endDate = endDate,
    createdAt = createdAt,
    membership = membership,
    status = status,
    bookingAllowanceMonthly = 0,
    bookingAllowanceRemainingMonthly = 0
)
