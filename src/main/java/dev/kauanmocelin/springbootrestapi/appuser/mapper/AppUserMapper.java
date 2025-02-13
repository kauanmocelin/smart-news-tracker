package dev.kauanmocelin.springbootrestapi.appuser.mapper;

import dev.kauanmocelin.springbootrestapi.appuser.AppUser;
import dev.kauanmocelin.springbootrestapi.appuser.request.AppUserPutRequestBody;
import dev.kauanmocelin.springbootrestapi.news.NewsMonitor;
import dev.kauanmocelin.springbootrestapi.news.request.KeywordRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppUserMapper {

    AppUser toAppUser(AppUserPutRequestBody appUserPutRequestBody);

    NewsMonitor toNewsMonitor(KeywordRequest keywordRequest);
}
