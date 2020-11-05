<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>

    <title>test klarna</title>

    <meta content="no-cache" http-equiv="cache-control">
    <meta content="no-cache" http-equiv="pragma">

    <style>

    </style>
    <script src="/lib/js/jquery-3.2.1.js" type="text/javascript"></script>
    <script src="https://x.klarnacdn.net/kp/lib/v1/api.js" async></script>
</head>

<body>
    <div id="klarna-payments-container"></div>
</body>

<script type="text/javascript">
    window.klarnaAsyncCallback = function () {
        // This is where you start calling Klarna's JS SDK functions
        $.getJSON('klarna/create-session.json', function (result) {
            console.log(result);
            let clientToken = result.client_token;
            Klarna.Payments.init({
                client_token: clientToken
            });

            let paymentMethodCategory = result.payment_method_categories[0].identifier;
            Klarna.Payments.load({
                container: '#klarna-payments-container',
                payment_method_category: paymentMethodCategory
            }, function (res) {
                console.log('load complete ===>');
                console.log(res);
                Klarna.Payments.authorize({
                    payment_method_category: paymentMethodCategory
                }, {
                    // customer: {
                    //     date_of_birth: "1970-01-01",
                    // }
                }, function(res) {
                    console.log('authorize complete ===>');
                    console.log(res);
                    let authorizationToken = res.authorization_token;
                    $.getJSON('klarna/place-order.json?authorizationToken=' + authorizationToken, function (result) {
                        console.log('place order complete ===>');
                        console.log(result);
                    });
                    // $.getJSON('klarna/create-customer-token.json?authorizationToken=' + authorizationToken, function (result) {
                    //     console.log('create customer token complete ===>');
                    //     console.log(result);
                    //     if (!!result.redirect_url) {
                    //         window.location.href = result.redirect_url;
                    //     }
                    // });
                })
            });

        });

    };
</script>

</html>
