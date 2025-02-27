package dev.kauanmocelin.springbootrestapi.appuser.mapper;

import dev.kauanmocelin.springbootrestapi.appuser.AppUser;
import dev.kauanmocelin.springbootrestapi.appuser.dto.AppUserPutRequestBody;
import dev.kauanmocelin.springbootrestapi.appuser.dto.AppUserResponseBody;
import dev.kauanmocelin.springbootrestapi.appuser.role.Role;
import dev.kauanmocelin.springbootrestapi.appuser.role.RoleType;
import dev.kauanmocelin.springbootrestapi.news.NewsMonitor;
import dev.kauanmocelin.springbootrestapi.news.request.KeywordRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AppUserMapper {

    AppUser toAppUser(AppUserPutRequestBody appUserPutRequestBody);

    @Mapping(target = "roles", source = "roles", qualifiedByName = "mapRoles")
    AppUserResponseBody toAppUserResponseBody(AppUser appUser);

    NewsMonitor toNewsMonitor(KeywordRequest keywordRequest);

    @Named("mapRoles")
    static List<RoleType> mapRoles(Collection<Role> roles) {
        return roles.stream().map(Role::getType).toList();
    }
}
