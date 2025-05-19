package com.example.demo.service;

import com.example.demo.entity.CollegeEntity;
import com.example.demo.repository.CollegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CollegeService
{
    @Autowired
    private CollegeRepository collegeRepository;

    public CollegeEntity addCollege(CollegeEntity collegeEntity)
    {
        return collegeRepository.save(collegeEntity);
    }

    public List<CollegeEntity> getAll()
    {
        return collegeRepository.findAll();
    }


    // NEW - Get sorted colleges
    public List<CollegeEntity> getSortedColleges(String sortBy) {
        List<CollegeEntity> colleges = collegeRepository.findAll();

        switch (sortBy.toLowerCase()) {
            case "name":
                colleges.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
                break;
            case "location":
                colleges.sort((c1, c2) -> c1.getLocation().compareToIgnoreCase(c2.getLocation()));
                break;
            case "ranking":
                colleges.sort((c1, c2) -> Integer.compare(c1.getRanking(), c2.getRanking()));
                break;
            case "accreditation":
                colleges.sort((c1, c2) -> {
                    if (c1.getAccreditation().isEmpty() || c2.getAccreditation().isEmpty()) return 0;
                    return c1.getAccreditation().get(0).compareToIgnoreCase(c2.getAccreditation().get(0));
                });
                break;
            case "courses":
                colleges.sort((c1, c2) -> {
                    if (c1.getCoursesOfferedWithFees().isEmpty() || c2.getCoursesOfferedWithFees().isEmpty()) return 0;
                    return c1.getCoursesOfferedWithFees().get(0).getCourseName().compareToIgnoreCase(c2.getCoursesOfferedWithFees().get(0).getCourseName());
                });
                break;
            case "fees":
                colleges.sort((c1, c2) -> {
                    if (c1.getCoursesOfferedWithFees().isEmpty() || c2.getCoursesOfferedWithFees().isEmpty()) return 0;
                    return Integer.compare(c1.getCoursesOfferedWithFees().get(0).getTuitionFee(), c2.getCoursesOfferedWithFees().get(0).getTuitionFee());
                });
                break;
            case "facilities":
                colleges.sort((c1, c2) -> {
                    if (c1.getFacilities().isEmpty() || c2.getFacilities().isEmpty()) return 0;
                    return c1.getFacilities().get(0).compareToIgnoreCase(c2.getFacilities().get(0));
                });
                break;
            default:
                // If unknown sort field, return unsorted
                break;
        }

        return colleges;
    }

    // NEW - Search colleges by keyword
    public List<CollegeEntity> searchColleges(String keyword) {
        List<CollegeEntity> colleges = collegeRepository.findAll();
        keyword = keyword.toLowerCase();

        String finalKeyword = keyword;
        return colleges.stream()
                .filter(college ->
                        college.getName().toLowerCase().contains(finalKeyword) ||
                                college.getLocation().toLowerCase().contains(finalKeyword) ||
                                college.getAccreditation().stream().anyMatch(acc -> acc.toLowerCase().contains(finalKeyword)) ||
                                college.getCoursesOfferedWithFees().stream().anyMatch(course -> course.getCourseName().toLowerCase().contains(finalKeyword)) ||
                                college.getFacilities().stream().anyMatch(facility -> facility.toLowerCase().contains(finalKeyword))
                )
                .toList();
    }


    // NEW - Get sorted + paginated colleges
    public List<CollegeEntity> getCollegesSortedAndPaginated(String sortBy, String direction, int page, int size) {
        Sort sort = direction.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<CollegeEntity> collegesPage = collegeRepository.findAll(pageable);
        return collegesPage.getContent();
    }

    // NEW - Search + Sort + Pagination
    public List<CollegeEntity> searchCollegesSortedPaginated(String keyword, String sortBy, String direction, int page, int size) {
        List<CollegeEntity> searchedColleges = collegeRepository.searchColleges(keyword);

        // Now manually sort and paginate since MongoRepository search doesn't auto paginate
        if (sortBy != null && !sortBy.isEmpty()) {
            if (direction.equalsIgnoreCase("desc")) {
                searchedColleges.sort((c1, c2) -> compareFields(c2, c1, sortBy));
            } else {
                searchedColleges.sort((c1, c2) -> compareFields(c1, c2, sortBy));
            }
        }

        // Pagination manually
        int start = page * size;
        int end = Math.min(start + size, searchedColleges.size());

        if (start > end) return List.of(); // empty list if page out of bounds
        return searchedColleges.subList(start, end);
    }

    private int compareFields(CollegeEntity c1, CollegeEntity c2, String sortBy) {
        switch (sortBy.toLowerCase()) {
            case "name":
                return c1.getName().compareToIgnoreCase(c2.getName());
            case "location":
                return c1.getLocation().compareToIgnoreCase(c2.getLocation());
            case "ranking":
                return Integer.compare(c1.getRanking(), c2.getRanking());
            case "fees":
                return Integer.compare(
                        c1.getCoursesOfferedWithFees().isEmpty() ? 0 : c1.getCoursesOfferedWithFees().get(0).getTuitionFee(),
                        c2.getCoursesOfferedWithFees().isEmpty() ? 0 : c2.getCoursesOfferedWithFees().get(0).getTuitionFee()
                );
            default:
                return 0;
        }
    }

    public List<CollegeEntity> filterCollegesByRankingAndFee(int maxRanking, int maxFee) {
        return collegeRepository.findByRankingAndFee(maxRanking, maxFee);
    }


    public boolean updateCollege(String id, CollegeEntity updatedCollege)
    {
        Optional<CollegeEntity>optionalCollege=collegeRepository.findById(id);
        if(optionalCollege.isPresent())
        {
            CollegeEntity existingCollege=optionalCollege.get();

            existingCollege.setName(updatedCollege.getName());
            existingCollege.setLocation(updatedCollege.getLocation());
            existingCollege.setRanking(updatedCollege.getRanking());
            existingCollege.setAccreditation(updatedCollege.getAccreditation());
            existingCollege.setEligibilityCriteria(updatedCollege.getEligibilityCriteria());
            existingCollege.setCoursesOfferedWithFees(updatedCollege.getCoursesOfferedWithFees());
            existingCollege.setFacilities(updatedCollege.getFacilities());

            collegeRepository.save(existingCollege);
            return true;
        }

        return false;
    }

    public boolean deleteCollegeById(String id) {
    Optional<CollegeEntity> college = collegeRepository.findById(id);
    if (college.isPresent()) {
        collegeRepository.delete(college.get());
        return true;
    }
    return false;
    }


    public Optional<CollegeEntity> findById(String id) {
        return collegeRepository.findById(id);
    }




}
