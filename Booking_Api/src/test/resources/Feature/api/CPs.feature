Feature: YAPE API


  Scenario Outline: Auth


    Given generamos el token
    Then el estado se encuentra activo "<statuscode>"

    Examples:
       | statuscode |
       | 200        |


  Scenario Outline: HealthCheck

    Given comprobamos que el api se encuentre activa "<url>"
    Then el estado se encuentra activo "<statuscode>"

    Examples:
      | url   | statuscode |
      | /ping | 201        |
      | /pin  | 404        |


  Scenario Outline: CreateBooking

    Given ingresamos los datos en el body "<firstname>""<lastname>""<totalprice>""<depositpaid>""<checkin>""<checkout>""<additionalneeds>"
    And el estado se encuentra activo "<statuscode>"
    Then se visualiza el bookingid del response

    Examples:
      |firstname | lastname  | totalprice| depositpaid| checkin  | checkout  | additionalneeds| statuscode |
      |  Joe     | Miranda   |11         | true       |2015-01-01|2019-01-15 | Breakfast      |      200   |



  Scenario Outline: GetBooking

    Given se ingresa el "<id>"
    And se visualiza el response
    Then el estado se encuentra activo "<statuscode>"

    Examples:
      |id    |statuscode|
      |1198  |200|
      |24185 |200|




  Scenario Outline: responseGetBookingName

    Given se envian los parametros name "<paramsname>""<paramslastname>""<checkin>""<checkout>"
    And el estado se encuentra activo "<statuscode>"
    Then se visualiza el arreglo bookingid del response

    Examples:
       |paramsname|paramslastname| checkin     |checkout    |statuscode |
       |Carlos    |Morante Briones|             |            |      200  |
       |          |               |  2018-01-01 | 2019-01-01 |      200  |
       |Carmen     |Olivera       |             |            |      200  |



  Scenario Outline: UpdateBooking


    Given generamos el token
    And ingresamos los datos en el body a actualizar "<id>""<firstname>""<lastname>""<totalprice>""<depositpaid>""<checkin>""<checkout>""<additionalneeds>"
    And el estado se encuentra activo "<statuscode>"
    Then se visualiza el response

    Examples:
      |id     |firstname | lastname      | totalprice| depositpaid| checkin  | checkout  | additionalneeds| statuscode |
      | 25282  |Carlos    |Morante Briones|111        |true        |2018-01-01|2019-01-01 |Breakfast       |200|



  Scenario Outline: PartialUpdateBooking


    Given generamos el token
    And ingresamos los datos a actualizar "<id>""<firstname>""<lastname>"
    And el estado se encuentra activo "<statuscode>"
    Then se visualiza el response

    Examples:
      | id    | firstname |lastname       | statuscode |
      | 25282  |Carlos    |Morante Briones |200         |




  Scenario Outline: DeleteBooking

    Given generamos el token
    And se ingresa el "<id>" para eliminar la reserva
    Then el estado se encuentra activo "<statuscode>"

    Examples:
      | id    | statuscode |
      | 25282 | 201        |
