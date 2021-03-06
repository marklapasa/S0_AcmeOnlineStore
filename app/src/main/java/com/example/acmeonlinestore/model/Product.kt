package com.example.acmeonlinestore.model

import kotlinx.serialization.Serializable
import java.text.NumberFormat

@Serializable
data class Product(
    val description: String? = null, // Apple MacBook Pro MF839LL/A 13.3-Inch Laptop with Retina Display (2.7 GHz Intel Core i5 Processor, 8 GB RAM, 128 GB Hard Drive, OS X Yosemite)
    val fullDescription: String? = null, // Just when you thought your MacBook Pro was state of the art, Apple introduces the MF839LL/A 13-inch MacBook Pro with new advanced processing power and graphics. New connectivity capabilities potentially speed large file transfers beyond any current connection. All MacBook Pro models are state-of-the-art. This new one takes it out to another edge. New Thunderbolt technology lets you connect high-performance peripherals and high-resolution displays to one port - with data transfer rates up to 10 Gbps. Thunderbolt is based on two fundamental technologies: PCI Express and DisplayPort. And because Thunderbolt is based on DisplayPort technology, the video standard for high-resolution displays, any Mini DisplayPort display plugs right into the Thunderbolt port. To connect a DisplayPort, DVI, HDMI, or VGA display, just use an existing optional adapter. Take everything from games to CAD to HD video projects anywhere. Time makes full-screen HD video calls that are astonishingly crisp. And thanks to the new widescreen format, you can get your friends in the picture, too. And with Mac OS X El Capitan and the latest iLife, you're sure to get a great Mac notebook - all in a precision aluminum unibody enclosure that's less than an inch thin.
    val id: String? = null, // 66a51a6eb5cd43128bde86ad87cae54c
    val name: String? = null, // MacBook Pro (13-inch)
    val price: Double? = null, // 1499
    val rating: Double? = null, // 4.5
    val seller: String? = null, // Apple
    val thumbnail: String? = null // https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcStGxGdj1QK5WSb5LIQuc49fvh4UMz2Kbj5sw&usqp=CAU
) {
    fun getPriceStr(): String? {
        return NumberFormat.getCurrencyInstance().format(price)
    }

    fun getRatingFloat() : Float? {
        return rating?.toFloat()
    }
}