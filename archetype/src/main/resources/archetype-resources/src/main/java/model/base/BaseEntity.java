#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.model.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Base entity class that can be extended for concrete domain models. It already
 * contains implementation of {@link IEntity} interface and Version field for
 * JPAs optimistic locking.
 * 
 * @author Ignas
 * 
 */
@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode(exclude = {"version"})
@JsonIgnoreProperties(value = { "version", "transient" })
public class BaseEntity implements IEntity, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Entity ID; Primary key in the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    /**
     * Version field for optimistic locking. If on update version is different
     * than it was when loaded that means someone already modified that entity and
     * concurrent modification exception is thrown.
     */
    @Version
    @JsonIgnore
    private Long version;

    /**
     * @return True if entity is not yet saved to the database.
     */
    @Override
    public boolean isTransient() {
        return id == null;
    }

}