package de.caritas.cob.consultingtypeservice.api.service;

import static org.assertj.core.api.Assertions.assertThat;

import de.caritas.cob.consultingtypeservice.api.repository.TopicRepository;
import de.caritas.cob.consultingtypeservice.api.tenant.TenantContext;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TopicServiceTest {

  @InjectMocks
  TopicService topicService;

  @Mock
  TopicRepository topicRepository;

  @AfterEach
  void tearDown() {
    TenantContext.clear();
  }

  @Test
  void getAllTopics_Should_CallRepository() {
    // given, when
    TenantContext.clear();
    topicService.getAllTopics();
    // then
    Mockito.verify(topicRepository).findAll();
  }
}