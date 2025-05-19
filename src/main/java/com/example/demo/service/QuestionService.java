package com.example.demo.service;

import com.example.demo.entity.CollegeEntity;
import com.example.demo.entity.QuestionEntity;
import com.example.demo.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService
{
    @Autowired
    private QuestionRepository questionRepository;

    public QuestionEntity addQuestion(QuestionEntity questionEntity)
    {
        return questionRepository.save(questionEntity);
    }

    public List<QuestionEntity> getAll()
    {
        return questionRepository.findAll();
    }

    public boolean updateQuestion(String id, QuestionEntity updatedQuestion)
    {
        Optional<QuestionEntity> optionalQuestion=questionRepository.findById(id);
        if(optionalQuestion.isPresent())
        {
            QuestionEntity existingQuestion=optionalQuestion.get();

            existingQuestion.setQuestion(updatedQuestion.getQuestion());
            existingQuestion.setOptions(updatedQuestion.getOptions());
            existingQuestion.setCorrectAnswer(updatedQuestion.getCorrectAnswer());
            existingQuestion.setSection(updatedQuestion.getSection());

            questionRepository.save(existingQuestion);
            return true;
        }

        return false;
    }

    public boolean deleteQuestionById(String id) {
        Optional<QuestionEntity> question = questionRepository.findById(id);
        if (question.isPresent()) {
            questionRepository.delete(question.get());
            return true;
        }
        return false;
    }


    public Optional<QuestionEntity> findById(String id) {
        return questionRepository.findById(id);
    }

}
