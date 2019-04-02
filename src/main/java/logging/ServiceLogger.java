package logging;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceLogger {
  private static Logger log = Logger.getLogger("GLOBAL_LOGGER");

  private ServiceLogger() {
    log.setLevel(Level.WARNING);
  }

  /**
   * Set the global logging level.
   */
  public void setLogLevel(int level) {
    Level loggingLevel;
    switch (level) {
      case 0:
        loggingLevel = Level.INFO;
        break;
      case 1:
        loggingLevel = Level.WARNING;
        break;
      case 2:
        loggingLevel = Level.SEVERE;
        break;
      default:
        loggingLevel = Level.OFF;
    }
    log.setLevel(loggingLevel);
  }

  public void log(String msg) {
    log.log(log.getLevel(), msg);
  }

  public static ServiceLogger getInstance() {
    return ServiceLoggerHelper.INSTANCE;
  }

  private static class ServiceLoggerHelper {
    private static final ServiceLogger INSTANCE = new ServiceLogger();
  }
}
