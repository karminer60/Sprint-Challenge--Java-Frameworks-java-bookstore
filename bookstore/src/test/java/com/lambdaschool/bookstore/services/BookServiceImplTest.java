package com.lambdaschool.bookstore.services;

import com.lambdaschool.bookstore.BookstoreApplication;
import com.lambdaschool.bookstore.exceptions.ResourceNotFoundException;
import com.lambdaschool.bookstore.models.Author;
import com.lambdaschool.bookstore.models.Book;
import com.lambdaschool.bookstore.models.Section;
import com.lambdaschool.bookstore.models.Wrote;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookstoreApplication.class)
//**********
// Note security is handled at the controller, hence we do not need to worry about security here!
//**********
public class BookServiceImplTest
{

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private SectionService sectionService;

    @Before
    public void setUp() throws
            Exception
    {
        MockitoAnnotations.initMocks(this);

        List<Book> myList = bookService.findAll();
        for(Book b: myList)
        {
            System.out.println(b.getBookid() + " " + b.getTitle());
        }
    }

    @After
    public void tearDown() throws
            Exception
    {
    }

    @Test
    public void findAll()
    {
        assertEquals(5,bookService.findAll().size());
    }

    @Test
    public void findBookById()
    {
        assertEquals("Test Essentials of Finance", bookService.findBookById(29).getTitle());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void notFindBookById()
    {
        TestCase.assertEquals("Test Eagle Cafe", bookService.findBookById(777).getTitle());
    }

    @Test
    public void delete()
    {
        bookService.delete(26);
        TestCase.assertEquals(4, bookService.findAll().size());
    }

    @Test
    public void save()
    {

        String sectionName = "genre";

        Author a2 = new Author();
        Section s1 = new Section(sectionName);
        s1.setSectionid(1);
        String book2Name = "Test This";
        Book b2 = new Book(book2Name, "9788489367012", 2007, s1);

        b2.getWrotes()
                .add(new Wrote(a2, new Book()));
        b2.setBookid(26);


        a2 = authorService.save(a2);
        s1 = sectionService.save(s1);
        b2 = bookService.save(b2);


        Book addBook = bookService.save(b2);
        assertNotNull(addBook);
        TestCase.assertEquals(book2Name, addBook.getTitle());

    }

    @Test
    public void update()
    {
    }

    @Test
    public void deleteAll()
    {
    }
}