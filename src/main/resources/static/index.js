import validering from "./validering.js"

$(() => {
    $("#loggInn").click(() => {
        if (validering.loginn()) {
            const url = "/loggInn?brukernavn=" + $("#brukernavn").val()
                + "&passord=" + $("#passord").val();

            $.get(url, OK => {
                if (OK) {
                    window.location.href = "/liste.html";
                } else {
                    $("#feil").html("Feil i brukernavn eller passord");
                }
            })
        }
    })

    $("#registrer").click(() => {
        window.location.href = "/nybruker.html";
    });

    $("#krypterAllePassord").click(() => {
        $.get("/krypterAllePassord", OK => {
            if (OK) {
                $("#feil").html("Kryptering fullført!")
            } else {
                $("#feil").html("Kryptering feilet!")
            }
        })
    })

    $("#brukernavn").on("change", () => validering.brukernavn());
    $("#passord").on("change", () => validering.passord());
});