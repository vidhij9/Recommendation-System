package recommendationSystem.contentFilters.tracktrace.service;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Component;

/**
 * Generates deterministic but unique tracking numbers that are easy to read when the API is used
 * without a database.
 */
@Component
public class TrackingNumberGenerator {

  private static final DateTimeFormatter FORMATTER =
      DateTimeFormatter.ofPattern("yyyyMMdd").withLocale(Locale.US).withZone(ZoneOffset.UTC);

  private final AtomicLong sequence = new AtomicLong(100_000);

  public String nextTrackingNumber() {
    final long nextValue = sequence.incrementAndGet();
    return "TRK-" + FORMATTER.format(java.time.Instant.now()) + "-" + String.format("%06d", nextValue);
  }
}
