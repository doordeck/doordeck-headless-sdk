%pythoncode %{
class ISecureStorage:
    def set_api_environment(self, api_environment: str) -> None:
        raise NotImplementedError

    def get_api_environment(self) -> str | None:
        raise NotImplementedError

    def add_cloud_auth_token(self, token: str) -> None:
        raise NotImplementedError

    def get_cloud_auth_token(self) -> str | None:
        raise NotImplementedError

    def add_cloud_refresh_auth_token(self, token: str) -> None:
        raise NotImplementedError

    def get_cloud_refresh_token(self) -> str | None:
        raise NotImplementedError

    def set_fusion_host(self, host: str) -> None:
        raise NotImplementedError

    def get_fusion_host(self) -> str | None:
        raise NotImplementedError

    def add_fusion_auth_token(self, token: str) -> None:
        raise NotImplementedError

    def get_fusion_auth_token(self) -> str | None:
        raise NotImplementedError

    def add_public_key(self, public_key: bytes) -> None:
        raise NotImplementedError

    def get_public_key(self) -> bytes | None:
        raise NotImplementedError

    def add_private_key(self, private_key: bytes) -> None:
        raise NotImplementedError

    def get_private_key(self) -> bytes | None:
        raise NotImplementedError

    def set_key_pair_verified(self, verified: bool) -> None:
        raise NotImplementedError

    def get_key_pair_verified(self) -> bool | None:
        raise NotImplementedError

    def add_user_id(self, user_id: str) -> None:
        raise NotImplementedError

    def get_user_id(self) -> str | None:
        raise NotImplementedError

    def set_user_email(self, user_email: str) -> None:
        raise NotImplementedError

    def get_user_email(self) -> str | None:
        raise NotImplementedError

    def add_certificate_chain(self, certificate_chain: typing.List[str]) -> None:
        raise NotImplementedError

    def get_certificate_chain(self) -> typing.List[str]:
        raise NotImplementedError

    def clear(self) -> None:
        raise NotImplementedError

class SecureStorage:
    Implementation: ISecureStorage | None = None

    static_buffer = ctypes.create_string_buffer(1024 * 10)

    py_set_data_type = ctypes.CFUNCTYPE(None, ctypes.c_void_p)
    py_get_data_type = ctypes.CFUNCTYPE(ctypes.c_char_p)

    @staticmethod
    def set_api_environment(result) -> None:
        if SecureStorage.Implementation is not None and result is not None:
            SecureStorage.Implementation.set_api_environment(ctypes.string_at(result).decode())

    @staticmethod
    def get_api_environment() -> int | None:
        if r := SecureStorage.Implementation and SecureStorage.Implementation.get_api_environment():
            SecureStorage.static_buffer.value = r.encode()
            return ctypes.addressof(SecureStorage.static_buffer)
        return None

    @staticmethod
    def add_cloud_auth_token(result) -> None:
        if SecureStorage.Implementation is not None and result is not None:
            SecureStorage.Implementation.add_cloud_auth_token(ctypes.string_at(result).decode())

    @staticmethod
    def get_cloud_auth_token() -> int | None:
        if r := SecureStorage.Implementation and SecureStorage.Implementation.get_cloud_auth_token():
            SecureStorage.static_buffer.value = r.encode()
            return ctypes.addressof(SecureStorage.static_buffer)
        return None

    @staticmethod
    def add_cloud_refresh_auth_token(result) -> None:
        if SecureStorage.Implementation is not None and result is not None:
            SecureStorage.Implementation.add_cloud_refresh_auth_token(ctypes.string_at(result).decode())

    @staticmethod
    def get_cloud_refresh_token() -> int | None:
        if r := SecureStorage.Implementation and SecureStorage.Implementation.get_cloud_refresh_token():
            SecureStorage.static_buffer.value = r.encode()
            return ctypes.addressof(SecureStorage.static_buffer)
        return None

    @staticmethod
    def set_fusion_host(result) -> None:
        if SecureStorage.Implementation is not None and result is not None:
            SecureStorage.Implementation.set_fusion_host(ctypes.string_at(result).decode())

    @staticmethod
    def get_fusion_host() -> int | None:
        if r := SecureStorage.Implementation and SecureStorage.Implementation.get_fusion_host():
            SecureStorage.static_buffer.value = r.encode()
            return ctypes.addressof(SecureStorage.static_buffer)
        return None

    @staticmethod
    def add_fusion_auth_token(result) -> None:
        if SecureStorage.Implementation is not None and result is not None:
            SecureStorage.Implementation.add_fusion_auth_token(ctypes.string_at(result).decode())

    @staticmethod
    def get_fusion_auth_token() -> int | None:
        if r := SecureStorage.Implementation and SecureStorage.Implementation.get_fusion_auth_token():
            SecureStorage.static_buffer.value = r.encode()
            return ctypes.addressof(SecureStorage.static_buffer)
        return None

    @staticmethod
    def add_public_key(result) -> None:
        if SecureStorage.Implementation is not None and result is not None:
            SecureStorage.Implementation.add_public_key(decode_base64_to_byte_array(ctypes.string_at(result).decode()))

    @staticmethod
    def get_public_key() -> int | None:
        if r := SecureStorage.Implementation and SecureStorage.Implementation.get_public_key():
            SecureStorage.static_buffer.value = encode_byte_array_to_base64(r).encode()
            return ctypes.addressof(SecureStorage.static_buffer)
        return None

    @staticmethod
    def add_private_key(result) -> None:
        if SecureStorage.Implementation is not None and result is not None:
            SecureStorage.Implementation.add_private_key(decode_base64_to_byte_array(ctypes.string_at(result).decode()))

    @staticmethod
    def get_private_key() -> int | None:
        if r := SecureStorage.Implementation and SecureStorage.Implementation.get_private_key():
            SecureStorage.static_buffer.value = encode_byte_array_to_base64(r).encode()
            return ctypes.addressof(SecureStorage.static_buffer)
        return None

    @staticmethod
    def set_key_pair_verified(result) -> None:
        if SecureStorage.Implementation is not None and result is not None:
            SecureStorage.Implementation.set_key_pair_verified(bool(ctypes.string_at(result).decode()))

    @staticmethod
    def get_key_pair_verified() -> int | None:
        if r := SecureStorage.Implementation and SecureStorage.Implementation.get_key_pair_verified():
            SecureStorage.static_buffer.value = str(r).encode()
            return ctypes.addressof(SecureStorage.static_buffer)
        return None

    @staticmethod
    def add_user_id(result) -> None:
        if SecureStorage.Implementation is not None and result is not None:
            SecureStorage.Implementation.add_user_id(ctypes.string_at(result).decode())

    @staticmethod
    def get_user_id() -> int | None:
        if r := SecureStorage.Implementation and SecureStorage.Implementation.get_user_id():
            SecureStorage.static_buffer.value = r.encode()
            return ctypes.addressof(SecureStorage.static_buffer)
        return None

    @staticmethod
    def set_user_email(result) -> None:
        if SecureStorage.Implementation is not None and result is not None:
            SecureStorage.Implementation.set_user_email(ctypes.string_at(result).decode())

    @staticmethod
    def get_user_email() -> int | None:
        if r := SecureStorage.Implementation and SecureStorage.Implementation.get_user_email():
            SecureStorage.static_buffer.value = r.encode()
            return ctypes.addressof(SecureStorage.static_buffer)
        return None

    @staticmethod
    def add_certificate_chain(result) -> None:
        if SecureStorage.Implementation is not None and result is not None:
            SecureStorage.Implementation.add_certificate_chain(string_to_certificate_chain(ctypes.string_at(result).decode()))

    @staticmethod
    def get_certificate_chain() -> int | None:
        if r := SecureStorage.Implementation and SecureStorage.Implementation.get_certificate_chain():
            SecureStorage.static_buffer.value = certificate_chain_to_string(r).encode()
            return ctypes.addressof(SecureStorage.static_buffer)
        return None

    @staticmethod
    def clear() -> None:
        if SecureStorage.Implementation is not None:
            SecureStorage.Implementation.clear()
%}