package dev.kauanmocelin.springbootrestapi.appuser.mapper;

import dev.kauanmocelin.springbootrestapi.appuser.AppUser;
import dev.kauanmocelin.springbootrestapi.appuser.request.AppUserPutRequestBody;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppUserMapper {

    AppUser toAppUser(AppUserPutRequestBody appUserPutRequestBody);
}
