package org.happyreaction.repositories.custom;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

/**
 * This class is used when DefaultRepositoryFactory overrides default repository factory class.
 * 
 * @author Ignas
 *
 * @param <T>
 *            Repository type.
 * @param <S>
 *            Entity type.
 * @param <ID>
 *            Entity ID type.
 */
public class DefaultRepositoryFactoryBean<T extends JpaRepository<S, ID>, S, ID extends Serializable>
		extends JpaRepositoryFactoryBean<T, S, ID> {

	public DefaultRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
		super(repositoryInterface);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected RepositoryFactorySupport createRepositoryFactory(EntityManager em) {
		return new DefaultRepositoryFactory(em);
	}

}