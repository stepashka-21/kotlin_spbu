import mu.KotlinLogging

val logger = KotlinLogging.logger {  }

fun main(args: Array<String>) {
    logger.info {"program started"}
    TODO()
    logger.info {"program completed"}
}