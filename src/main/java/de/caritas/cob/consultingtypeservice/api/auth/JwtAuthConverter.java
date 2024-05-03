package de.caritas.cob.consultingtypeservice.api.auth;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

  private final @NonNull AuthorisationService authorisationService;

  private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter =
      new JwtGrantedAuthoritiesConverter();

  private final JwtAuthConverterProperties properties;


  @Override
  public AbstractAuthenticationToken convert(Jwt jwt) {
    var authorities = getGrantedAuthorities(jwt);
    return new JwtAuthenticationToken(jwt, authorities, getPrincipalClaimName(jwt));
  }

  private Collection<GrantedAuthority> getGrantedAuthorities(Jwt jwt) {
    Collection<GrantedAuthority> convertedGrantedAuthorities =
        jwtGrantedAuthoritiesConverter.convert(jwt);
    if (convertedGrantedAuthorities != null) {
      return Stream.concat(
              convertedGrantedAuthorities.stream(),
              authorisationService.extractRealmAuthorities(jwt).stream())
          .collect(Collectors.toSet());
    } else {
      return authorisationService.extractRealmAuthorities(jwt);
    }
  }

  private String getPrincipalClaimName(Jwt jwt) {
    String claimName = JwtClaimNames.SUB;
    if (properties.getPrincipalAttribute() != null) {
      claimName = properties.getPrincipalAttribute();
    }
    return jwt.getClaim(claimName);
  }
}
