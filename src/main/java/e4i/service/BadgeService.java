package e4i.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import e4i.domain.Badge;
import e4i.repository.BadgeRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Badge}.
 */
@Service
@Transactional
public class BadgeService {

    private final Logger log = LoggerFactory.getLogger(BadgeService.class);

    private final BadgeRepository badgeRepository;

    public BadgeService(BadgeRepository badgeRepository) {
        this.badgeRepository = badgeRepository;
    }

    /**
     * Save a badge.
     *
     * @param badge the entity to save.
     * @return the persisted entity.
     */
    public Badge save(Badge badge) {
        log.debug("Request to save Badge : {}", badge);
        return badgeRepository.save(badge);
    }

    /**
     * Get all the badges.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Badge> findAll() {
        log.debug("Request to get all Badges");
        return badgeRepository.findAll();
    }


    /**
     * Get one badge by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Badge> findOne(Long id) {
        log.debug("Request to get Badge : {}", id);
        return badgeRepository.findById(id);
    }

    /**
     * Delete the badge by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Badge : {}", id);
        badgeRepository.deleteById(id);
    }
}
