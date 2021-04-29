import validering from "./validering.js"

$(() => {

    $("#brukernavn").on("change", () => validering.brukernavn());
    $("#passord").on("change", () => validering.passord());
});