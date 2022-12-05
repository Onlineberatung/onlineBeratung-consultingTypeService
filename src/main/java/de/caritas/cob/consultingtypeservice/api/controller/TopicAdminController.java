package de.caritas.cob.consultingtypeservice.api.controller;

import de.caritas.cob.consultingtypeservice.api.auth.AuthenticatedUser;
import de.caritas.cob.consultingtypeservice.api.model.TopicMultilingualDTO;
import de.caritas.cob.consultingtypeservice.api.service.TopicServiceFacade;
import de.caritas.cob.consultingtypeservice.generated.api.controller.TopicadminApi;
import io.swagger.annotations.Api;
import java.util.List;
import javax.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Api(tags = "topic-controller")
@Slf4j
public class TopicAdminController implements TopicadminApi {

  private final @NonNull TopicServiceFacade topicServiceFacade;

  @Autowired
  private AuthenticatedUser authenticatedUser;
  @Override
  @PreAuthorize("hasAuthority('topic-admin')")
  public ResponseEntity<TopicMultilingualDTO> createTopic(@Valid TopicMultilingualDTO topicMultilingualDTO) {
    log.info("Creating topic by user {} ", authenticatedUser.getUsername());
    TopicMultilingualDTO savedTopic = topicServiceFacade.createTopic(topicMultilingualDTO);
    return new ResponseEntity<>(savedTopic, HttpStatus.OK);
  }

  @Override
  @PreAuthorize("hasAuthority('topic-admin')")
  public ResponseEntity<TopicMultilingualDTO> updateTopic(Long id, @Valid TopicMultilingualDTO topicMultilingualDTO) {
    log.info("Updating topic with id {} by user {} ", id, authenticatedUser.getUsername());
    TopicMultilingualDTO savedTopic = topicServiceFacade.updateTopic(id, topicMultilingualDTO);
    return new ResponseEntity<>(savedTopic, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<List<TopicMultilingualDTO>> getAllTopicsWithTranslation() {
    return TopicadminApi.super.getAllTopicsWithTranslation();
  }

  @Override
  public ResponseEntity<TopicMultilingualDTO> getTopicWithTranslationById(Long id) {
    return TopicadminApi.super.getTopicWithTranslationById(id);
  }
}
