package de.caritas.cob.consultingtypeservice.api.model;

import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "consulting_types")
public class ConsultingTypeEntity extends ConsultingType {}
