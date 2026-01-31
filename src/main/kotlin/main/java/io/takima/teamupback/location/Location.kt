package main.java.io.takima.teamupback.location

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "location")
class Location(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_id_seq")
    @SequenceGenerator(name = "location_id_seq", sequenceName = "location_id_seq", allocationSize = 1)
    val id: Int? = null,

    @Column(name = "name", length = 150)
    var name: String? = null,

    @Column(name = "address")
    var address: String? = null,

    @Column(name = "average_price", precision = 10, scale = 2)
    var averagePrice: BigDecimal? = null,

    @Column(name = "picture_url")
    var pictureUrl: String? = null
)
