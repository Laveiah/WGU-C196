package com.example.c196.Database;

import android.app.AlertDialog;
import android.app.Application;

import com.example.c196.Entity.Mentor;
import com.example.c196.dao.AssessmentDAO;
import com.example.c196.dao.CourseDAO;
import com.example.c196.dao.MentorDAO;
import com.example.c196.dao.TermDAO;
import com.example.c196.Entity.Assessment;
import com.example.c196.Entity.Course;
import com.example.c196.Entity.Term;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private AssessmentDAO mAssessmentDAO;
    private CourseDAO mCourseDAO;
    private MentorDAO mMentorDAO;
    private TermDAO mTermDAO;

    private List<Assessment> mAllAssessments;
    private List<Course> mAllCourses;
    private List<Course> mAllTermCourses;
    private List<Assessment> mAllAssessmentCourses;
    private List<Mentor> mAllMentors;
    private List<Term> mAllTerms;

    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        c196MobileApplicationDataBase db = c196MobileApplicationDataBase.getDataBase(application);

        mAssessmentDAO = db.assessmentDAO();
        mCourseDAO = db.courseDAO();
        mMentorDAO = db.mentorDAO();
        mTermDAO = db.termDAO();
    }

    //******************************ASSESSMENT SECTION********************************************//
    public List<Assessment> getAllAssessments() {
        databaseExecutor.execute(() -> {
            mAllAssessments = mAssessmentDAO.getAllAssessments();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllAssessments;
    }

    public List<Assessment> getAssessmentByCourseID(int id) {//////////////////
        databaseExecutor.execute(() -> {
            mAllAssessmentCourses = mAssessmentDAO.getAssessmentByCourseID(id);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllAssessmentCourses;
    }/////////////////////////////////////////////////

    public void insert(Assessment assessment) {
        databaseExecutor.execute(() -> {
            mAssessmentDAO.insert(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Assessment assessment) {
        databaseExecutor.execute(() -> {
            mAssessmentDAO.update(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Assessment assessment) {
        databaseExecutor.execute(() -> {
            mAssessmentDAO.delete(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//******************************Course SECTION********************************************//

     public List<Course> getAllCourses() {
         databaseExecutor.execute(() -> {
            mAllCourses = mCourseDAO.getAllCourses();
         });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return mAllCourses;
        }
    public List<Course> getCoursesByTerm(int id) {
        databaseExecutor.execute(() -> {
            mAllTermCourses = mCourseDAO.getCoursesByTerm(id);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllTermCourses;
    }


    public void insert(Course course) {
        databaseExecutor.execute(() -> {
            mCourseDAO.insert(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Course course) {
        databaseExecutor.execute(() -> {
            mCourseDAO.update(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Course course) {
        databaseExecutor.execute(() -> {
            mCourseDAO.delete(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //******************************Mentor SECTION********************************************//

    public List<Mentor> getAllMentors() {
        databaseExecutor.execute(() -> {
            mAllMentors = mMentorDAO.getAllMentors();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllMentors;
    }

    public void insert(Mentor mentor) {
        databaseExecutor.execute(() -> {
            mMentorDAO.insert(mentor);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Mentor mentor) {
        databaseExecutor.execute(() -> {
            mMentorDAO.update(mentor);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Mentor mentor) {
        databaseExecutor.execute(() -> {
            mMentorDAO.delete(mentor);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//******************************Term SECTION********************************************//

    public List<Term> getAllTerms() {
        databaseExecutor.execute(() -> {
         mAllTerms = mTermDAO.getAllTerms();
        });
        try {
         Thread.sleep(1000);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
        return mAllTerms;
    }

    public void insert(Term term) {
        databaseExecutor.execute(() -> {
            mTermDAO.insert(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Term term) {
        databaseExecutor.execute(() -> {
            mTermDAO.update(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Term term) {
        if (getCoursesByTerm(term.getTermID()).size()==0){
            databaseExecutor.execute(() -> {
                mTermDAO.delete(term);
            });
        } else {
            //alert
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
