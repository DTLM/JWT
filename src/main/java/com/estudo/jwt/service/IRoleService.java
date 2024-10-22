package com.estudo.jwt.service;

import com.estudo.jwt.modal.Role;

public interface IRoleService {

	Role getByNome(String nome);

	Role findById(Long id) throws Exception;

	void Save(Role role);
}
