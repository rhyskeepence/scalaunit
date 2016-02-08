package scalaunit.framework

private[scalaunit] object Timer {
  def start() = new Timer(System.currentTimeMillis())
}
private[scalaunit] case class Timer(startMs: Long) {
  def elapsedMs: Long = System.currentTimeMillis() - startMs
}