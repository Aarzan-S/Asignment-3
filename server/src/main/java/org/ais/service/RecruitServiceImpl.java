package org.ais.service;

import org.ais.model.Recruit;
import org.ais.model.Response;
import org.ais.repository.RecruitRepository;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class RecruitServiceImpl implements RecruitService {
    private static RecruitServiceImpl instance;

    public static RecruitServiceImpl getInstance() {
        if (instance == null) {
            instance = new RecruitServiceImpl();
        }
        return instance;
    }

    @Override
    public LinkedList<Recruit> getAllRecruits() throws SQLException {
        RecruitRepository repository = RecruitRepository.getInstance();
        LinkedList<Recruit> recruits = repository.fetchAllRecruits();
//        return sortByQualification(recruits);
    return recruits;
    }

    @Override
    public Response updateRecruit(Recruit recruit, int id, boolean isStaff) {
        RecruitRepository repository = RecruitRepository.getInstance();
        if (isStaff)
            repository.updateDetailsByStaff(recruit, id);
        else
            repository.updateDetails(recruit, id);
        return new Response("Recruit  data updated", LocalDateTime.now(), "SUCCESS");
    }

    @Override
    public LinkedList<Recruit> getRecruitHistory() {
        RecruitRepository repository = RecruitRepository.getInstance();
        LinkedList<Recruit> recruits = repository.fetchRecruitHistory();
        return sortByQualification(recruits);
    }

    @Override
    public Response registerRecruit(Recruit recruit) {
        RecruitRepository repository = RecruitRepository.getInstance();
        if (repository.doesUserExists(recruit)) {
            return new Response("ERROR : Username already exits", LocalDateTime.now(), "FAILURE");
        }
        repository.register(recruit);
        return new Response("Recruit data added", LocalDateTime.now(), "SUCCESS");

    }

    @Override
    public Response updateDetails(Recruit recruit, int id) {
        RecruitRepository repository = RecruitRepository.getInstance();
        repository.updateDetails(recruit, id);
        return new Response("Recruit  data updated", LocalDateTime.now(), "SUCCESS");
    }

    @Override
    public Recruit getDetails(String username) {
        RecruitRepository repository = RecruitRepository.getInstance();
        return repository.getRecruitDetails(username);
    }

    private LinkedList<Recruit> sortByQualification(LinkedList<Recruit> students) {
        Comparator<Recruit> educationLevelComparator = Comparator.comparing(student -> {
            switch (student.getHighestQualification()) {
                case "PhD":
                    return 3;
                case "Masters":
                    return 2;
                case "Bachelors":
                    return 1;
                default:
                    return 0;
            }
        }, Comparator.reverseOrder());

        return students.stream()
                .sorted(educationLevelComparator)
                .collect(Collectors.toCollection(LinkedList::new));
    }

}