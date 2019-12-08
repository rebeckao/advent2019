class Day8SpaceImageFormat {
    fun decodedImage(rawLayers : String, width: Int, height: Int) : String {
        val layerLength = width * height
        val layers = rawLayers.chunked(layerLength)
        val decodedImage = Array(height) {IntArray(width)}
        for (x in 0 until width) {
            for (y in 0 until height) {
                for (layer in layers) {
                    val currentLayerColor = layer[y * width + x]
                    if (currentLayerColor == '0' || currentLayerColor == '1') {
                        decodedImage[y][x] = currentLayerColor.toString().toInt()
                        break
                    }
                }
            }
        }
        return decodedImage.asSequence()
            .map{it.joinToString("")}
            .joinToString("\n")
    }
}