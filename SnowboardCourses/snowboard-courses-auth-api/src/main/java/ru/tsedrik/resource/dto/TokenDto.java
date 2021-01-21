package ru.tsedrik.resource.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Mодель для выдачи сгенерированного токена")
public class TokenDto {

    @ApiModelProperty(value = "Токен пользователя", example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiYXVkIjoic25vd2JvYXJkLWNvdXJzZXMtYXV0aCIsInJvbGUiOiJST0xFX1VTRVIiLCJpc3MiOiJzbm93Ym9hcmQtY291cnNlcy1hdXRoIiwiZXhwIjoxNjEwOTIzMjA4LCJpYXQiOjE2MTA5MjI5MDgsImp0aSI6ImQwNDU2OTg1LWJkYjQtNDMyNy04NjZkLWNkZTYxZTYwYTE5MyIsImVtYWlsIjoidXNlckBnbWFpbC5jb20ifQ.hbJS8Re6nyi5DquOYvclhFfCL1vLx4HQeCjv24WX8Ng", required = true)
    private String token;

    public TokenDto() {
    }

    public TokenDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
