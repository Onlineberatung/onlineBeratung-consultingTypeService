package de.caritas.cob.consultingtypeservice.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.google.common.collect.Lists;
import de.caritas.cob.consultingtypeservice.api.model.ApplicationSettingsEntity;
import de.caritas.cob.consultingtypeservice.api.repository.ApplicationSettingsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ApplicationSettingsServiceTest {

  @Mock ApplicationSettingsRepository applicationSettingsRepository;

  @InjectMocks ApplicationSettingsService applicationSettingsService;

  @Test
  void getApplicationSettings_Should_ReturnSettingsFromDatabase() {
    // given
    ApplicationSettingsEntity applicationSettingsEntity = new ApplicationSettingsEntity();
    Mockito.when(applicationSettingsRepository.findAll())
        .thenReturn(Lists.newArrayList(applicationSettingsEntity));
    // when
    var settings = applicationSettingsService.getApplicationSettings();
    // then
    assertThat(settings).contains(applicationSettingsEntity);
  }

  @Test
  void getApplicationSettings_Should_ReturnOptionalEmpty_When_NoSettingsFoundInDatabase() {
    // when, then
    assertThat(applicationSettingsService.getApplicationSettings()).isEmpty();
  }

  @Test
  void getApplicationSettings_Should_ThrowException_When_MoreThanOneSettingsFoundInDatabase() {
    // given
    Mockito.when(applicationSettingsRepository.findAll())
        .thenReturn(
            Lists.newArrayList(new ApplicationSettingsEntity(), new ApplicationSettingsEntity()));
    // when, then
    assertThrows(
        IllegalStateException.class, () -> applicationSettingsService.getApplicationSettings());
  }
}
