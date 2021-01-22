<%@ page import="java.util.Random" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.TreeSet" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%-- JSTL core library https://docs.oracle.com/javaee/5/jstl/1.1/docs/tlddocs/ --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- JSTL formatting library https://docs.oracle.com/javaee/5/jstl/1.1/docs/tlddocs/ --%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%-- JSTL functions library https://docs.oracle.com/javaee/5/jstl/1.1/docs/tlddocs/ --%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%--JSP Tag Reference https://docs.oracle.com/cd/E13226_01/workshop/docs81/pdf/files/workshop/JSPTagsReference.pdf  --%>

<!DOCTYPE html>
<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">

    <title>Lotto Quick Pick</title>

</head>
<body>

<%
    // Check if the page is being accessed using a post operation
    if (request.getMethod().equalsIgnoreCase("post")) {
        // Retrieve the form field parameter for lottoType and quantity
        String lottoType = request.getParameter("lottoType");
        String quantityString = request.getParameter("quantity");
        int quantity = Integer.parseInt(quantityString);

        // Create a Random object for generating random numbers
        Random rand = new Random();

        if (lottoType.equalsIgnoreCase("lotto649")) {
            // Output the lotto quick pick numbers
            out.print("<h2>Lotto 649 Quick Picks</h2>");


            // Generate Lotto649 quick picks
            //   1) Display 6 unique Lotto 649 random numbers from 1 to 49 sorted in ascending order (no duplicates allowed)
            // Create a Set to a collection of unique values that is sorted
            Set<Integer> lotto649Set = new TreeSet<>();
            // Create constants for Lotto 649
            final int LOTTO649_MAX_VALUE = 49;
            final int LOTTO649_SELECTION_COUNT = 6;
            // Generate 6 random numbers and add it to our set
            while (lotto649Set.size() < LOTTO649_SELECTION_COUNT) {
                // Generate a random number between 1 and 49
                int randomNumber = rand.nextInt(LOTTO649_MAX_VALUE) + 1;
                // Add the randomNumber to our set
                lotto649Set.add(randomNumber);
            }
            for (Integer singleNumber : lotto649Set) {
                out.print(singleNumber + " ");
            }

        } else if (lottoType.equalsIgnoreCase("lottoMax")) {
            // Generate LottoMAX quick picks
        } else {
            // Invalid LottoType selection

        }

    }


    //  - Hint: Use a Set store unique values. Use a TreeSet to sorted the unique values.
    //  2) Display 7 unique Lotto MAX random numbers from 1 to 50 sorted in ascending order (no duplicates allowed)

%>

    <!-- TODO: Create an HTML form that allows the user to select either Lotto649 or LottoMAX quick picks
        and to specify the quantity of quick picks.
        Hint: To check if the page is being accessed from a post operation:

        if (request.getMethod().equals("post")) {
            String lottoType = request.getParameter("lottoType");

        }
     -->
<main class="container">
    <div class="display-4">
        <h1>Lotto Quick Pick</h1>
    </div>

    <form method="post">
        <fieldset class="row mb-3">
            <legend class="col-form-label col-sm-2 pt-0">Lottery Type</legend>
            <div class="col-sm-10">
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" id="lotto649Radio" name="lottoType" value="lotto649" checked="checked" >
                    <label class="form-check-label" for="lotto649Radio" >Lotto 649</label>
                </div>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" id="lottoMaxRadio" name="lottoType" value="lottoMax" >
                    <label class="form-check-label" for="lottoMaxRadio" >Lotto MAX</label>
                </div>
            </div>
        </fieldset>
        <div class="row mb-3">
            <label for="quantity" class="form-label col-sm-2">Quantity</label>
            <div class="col-sm-10">
                <input type="number" id="quantity" name="quantity" min="1" max="10" value="1" class="form-control" >
            </div>
        </div>
        <button type="submit" class="btn btn-primary mb-3">Generate</button>
    </form>
</main>

<!-- Optional JavaScript: Bootstrap Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW"
        crossorigin="anonymous"></script>

</body>

</html>