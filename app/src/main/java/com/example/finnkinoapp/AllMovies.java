package com.example.finnkinoapp;


import static javax.xml.parsers.DocumentBuilderFactory.newInstance;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;


public class AllMovies
{
    private String ID = "1029";
    private String date;
    private ArrayList<Movie> movie_array = new ArrayList<Movie>();
    private ArrayList<Movie> newMovie_array = new ArrayList<Movie>();


    public AllMovies()
    {

    }


    public void getDate()
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDateTime today = LocalDateTime.now();
        date = dtf.format(today);
    }


    public void setDate(String dt)
    {
        date = dt;
    }


    public void setID(String newID)
    {
        ID = newID;
    }


    public void readXML()
    {
        try
        {
            DocumentBuilder builder = newInstance().newDocumentBuilder();
            String urlString = "https://www.finnkino.fi/xml/Schedule/?area=" + ID + "&dt=" + date;
            Document doc = builder.parse(urlString);
            doc.getDocumentElement().normalize();
            NodeList nlist = doc.getElementsByTagName("Show");

            for (int i = 0; i < nlist.getLength(); i++)
            {
                Node node = nlist.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element element = (Element) node;

                    movie_array.add(new Movie(element.getElementsByTagName("Title").item(0).getTextContent(),
                            element.getElementsByTagName("Theatre").item(0).getTextContent(),
                            element.getElementsByTagName("dttmShowStart").item(0).getTextContent(),
                            element.getElementsByTagName("TheatreAuditorium").item(0).getTextContent(),
                            element.getElementsByTagName("LengthInMinutes").item(0).getTextContent(),
                            element.getElementsByTagName("Rating").item(0).getTextContent()));
                }
            }
        }
        catch (ParserConfigurationException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (SAXException e)
        {
            e.printStackTrace();
        }
    }


    public void compareTime(String sTime, String eTime)
    {
        try
        {
            Date timeFrom;
            Date timeTo;
            Date newTime;

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

            if (!sTime.isEmpty() && !eTime.isEmpty())
            {
                timeFrom = sdf.parse(sTime);
                timeTo = sdf.parse(eTime);

                for (int i = 0; i < movie_array.size(); i++)
                {
                    newTime = sdf.parse(movie_array.get(i).getStartTime());

                    if (newTime.after(timeFrom) && newTime.before(timeTo))
                    {
                        newMovie_array.add(movie_array.get(i));
                    }
                }
            }
            else if (sTime.isEmpty() && !eTime.isEmpty())
            {
                timeTo = sdf.parse(eTime);

                for (int i = 0; i < movie_array.size(); i++)
                {
                    newTime = sdf.parse(movie_array.get(i).getStartTime());

                    if (newTime.before(timeTo))
                    {
                        newMovie_array.add(movie_array.get(i));
                    }
                }
            }
            else if (!sTime.isEmpty() && eTime.isEmpty())
            {
                timeFrom = sdf.parse(sTime);

                for (int i = 0; i < movie_array.size(); i++)
                {
                    newTime = sdf.parse(movie_array.get(i).getStartTime());

                    if (newTime.after(timeFrom))
                    {
                        newMovie_array.add(movie_array.get(i));
                    }
                }
            }
            else
            {
                for (int i = 0; i < movie_array.size(); i++)
                {
                    newMovie_array.add(movie_array.get(i));
                }
            }
        }

        catch (ParseException e)
        {
            e.printStackTrace();
        }
    }


    public void clearArrays()
    {
        movie_array.clear();
        newMovie_array.clear();
    }


    public ArrayList<Movie> getArray()
    {
        return newMovie_array;
    }
}
