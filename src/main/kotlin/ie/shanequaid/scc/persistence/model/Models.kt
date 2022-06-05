package ie.shanequaid.scc.persistence.model

import ie.shanequaid.scc.persistence.model.dto.ImageDTO
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Season (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val name: String? = null
)

@Entity
class Size (
    @Id
    val id: Long? = null,

    @Column(insertable = false, updatable = false)
    val name: String
)

@Entity
class Color (
    @Id
    val id: Long,
    val name: String? = null
)

@Entity
class Image (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val path: String? = null,
    @Column(updatable = false)
    val fileName: String? = null
) {
}