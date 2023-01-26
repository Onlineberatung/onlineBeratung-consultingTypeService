package de.caritas.cob.consultingtypeservice.api.admin.service;

import de.caritas.cob.consultingtypeservice.api.admin.hallink.ConsultingTypePaginationLinksBuilder;
import de.caritas.cob.consultingtypeservice.api.consultingtypes.ConsultingTypeRepositoryService;
import de.caritas.cob.consultingtypeservice.api.mapper.ConsultingTypeMapper;
import de.caritas.cob.consultingtypeservice.api.mapper.ExtendedConsultingTypeMapper;
import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypeAdminResultDTO;
import de.caritas.cob.consultingtypeservice.api.model.ExtendedConsultingTypeResponseDTO;
import de.caritas.cob.consultingtypeservice.api.model.PaginationLinks;
import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

/** Service class to handle administrative operations on consulting types. */
@Service
@RequiredArgsConstructor
public class ConsultingTypeAdminService {

  private final @NonNull ConsultingTypeRepositoryService consultingTypeRepositoryService;

  /**
   * Returns all dioceses within the given page and perPage offsets.
   *
   * @param page Number of page where to start in the query (1 = first page) (required) * @param
   * @param perPage Number of items which are being returned per page (required)
   * @return {@link ConsultingTypeAdminResultDTO}
   */
  public ConsultingTypeAdminResultDTO findConsultingTypes(Integer page, Integer perPage) {
    PagedListHolder<ExtendedConsultingTypeResponseDTO> pagedListHolder =
        new PagedListHolder<>(fullSortedExtendedConsultingTypeResponseList());
    pagedListHolder.setPageSize(Math.max(perPage, 1));
    pagedListHolder.setPage(currentPage(page, pagedListHolder));

    return new ConsultingTypeAdminResultDTO()
        .embedded(
            page > pagedListHolder.getPageCount()
                ? Collections.emptyList()
                : pagedListHolder.getPageList())
        .links(buildPaginationLinks(page, perPage, pagedListHolder))
        .total(pagedListHolder.getNrOfElements());
  }

  private List<ExtendedConsultingTypeResponseDTO> fullSortedExtendedConsultingTypeResponseList() {

    return consultingTypeRepositoryService.getListOfConsultingTypes().stream()
        .sorted(Comparator.comparing(ConsultingType::getSlug))
        .map(
            ct ->
                ConsultingTypeMapper.mapConsultingType(
                    ct, ExtendedConsultingTypeMapper::mapConsultingType))
        .collect(Collectors.toList());
  }

  private Integer currentPage(
      Integer page, PagedListHolder<ExtendedConsultingTypeResponseDTO> pagedListHolder) {
    return Math.max(page < pagedListHolder.getPageCount() ? page - 1 : page, 0);
  }

  private PaginationLinks buildPaginationLinks(
      Integer page,
      Integer perPage,
      PagedListHolder<ExtendedConsultingTypeResponseDTO> pagedListHolder) {
    return ConsultingTypePaginationLinksBuilder.getInstance()
        .withPage(page)
        .withPerPage(perPage)
        .withPagedListHolder(pagedListHolder)
        .buildPaginationLinks();
  }
}
