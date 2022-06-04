package ie.shanequaid.scc.persistence.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Season {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null
    private val name: String? = null
}

@Entity
class Size {
    @Id
    private val id: Long? = null

    @Column(insertable = false, updatable = false)
    private val name: String? = null
}

@Entity
class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null
    private val name: String? = null
}

@Entity
class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null
    private val path: String? = null
    private val fileName: String? = null
}

@Entity
class Occasion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null
    private val name: String? = null
}