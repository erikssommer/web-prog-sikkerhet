package com.eriksommer.webprogsikkerhet.repository;

import com.eriksommer.webprogsikkerhet.model.Bil;
import com.eriksommer.webprogsikkerhet.model.Bruker;
import com.eriksommer.webprogsikkerhet.model.Motorvogn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@SuppressWarnings("all")
public class MotorvognRepository {

    @Autowired
    private JdbcTemplate db;

    private Logger logger = LoggerFactory.getLogger(MotorvognRepository.class);

    public boolean lagreMotorvogn(Motorvogn m) {
        String sql = "INSERT INTO Motorvogn (personnr,navn,adresse,kjennetegn,merke,type) VALUES(?,?,?,?,?,?)";
        try {
            db.update(sql, m.getPersonnr(), m.getNavn(), m.getAdresse(), m.getKjennetegn(), m.getMerke(), m.getType());
            return true;
        } catch (Exception e) {
            logger.error("Feil i lagre motorvogn " + e);
            return false;
        }
    }

    public List<Motorvogn> hentAlleMotorvogner() {
        String sql = "SELECT * FROM Motorvogn";
        try {
            return db.query(sql, new BeanPropertyRowMapper(Motorvogn.class));
        } catch (Exception e) {
            logger.error("Feil i hent alle motorvogner " + e);
            return null;
        }
    }

    public Motorvogn henteEnMotorvogn(int id) {
        String sql = "SELECT * FROM Motorvogn WHERE id=?";
        try {
            List<Motorvogn> enMotorvogn = db.query(sql, new BeanPropertyRowMapper(Motorvogn.class), id);
            return enMotorvogn.get(0);
        } catch (Exception e) {
            logger.error("Feil i hent en motorvogn " + e);
            return null;
        }
    }

    public boolean endreMotorvogn(Motorvogn m) {
        String sql = "UPDATE Motorvogn SET personnr=?, navn=?,adresse=?,kjennetegn=?,merke=?,type=? where id=?";
        try {
            db.update(sql, m.getPersonnr(), m.getNavn(), m.getAdresse(), m.getKjennetegn(), m.getMerke(), m.getType(), m.getId());
            return true;
        } catch (Exception e) {
            logger.error("Feil i endre en motorvogn " + e);
            return false;
        }
    }

    public boolean slettEnMotorvogn(String personnr) {
        String sql = "DELETE FROM Motorvogn WHERE personnr=?";
        try {
            db.update(sql, personnr);
            return true;
        } catch (Exception e) {
            logger.error("Feil i slett en motorvogn" + e);
            return false;
        }
    }

    public boolean slettAlleMotorvogner() {
        String sql = "DELETE FROM Motorvogn";
        try {
            db.update(sql);
            return true;
        } catch (Exception e) {
            logger.error("Feil i slett alle motorvogner" + e);
            return false;
        }
    }

    public List<Bil> hentAlleBiler() {
        String sql = "SELECT * FROM Bil";
        try {
            return db.query(sql, new BeanPropertyRowMapper(Bil.class));
        } catch (Exception e) {
            return null;
        }
    }

    public boolean loggInn(String brukernavn, String passord){
        String sql = "SELECT * FROM Bruker WHERE brukernavn = ?";

        try {
            List<Bruker> list = db.query(sql, new BeanPropertyRowMapper(Bruker.class), brukernavn);

            if (list != null){
                if (sjekkPassord(passord, list.get(0).getPassord())){
                    return true;
                }
            }
            return false;
        }catch (Exception e){
            logger.error(e.getMessage());
            return false;
        }
    }

    public boolean sjekkPassord(String passord, String hashPassword){
        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder(15);

        return bCrypt.matches(passord, hashPassword);
    }

    public boolean registrerBruker(Bruker bruker){
        String sql = "INSERT INTO Bruker (brukernavn, passord) VALUES (?,?)";

        try {
            String kryptertPassord = krypterPassord(bruker.getPassord());
            db.update(sql, bruker.getBrukernavn(), kryptertPassord);
            return true;
        }catch (Exception e) {
            logger.error("Kunne ikke lagre bruker");
            return false;
        }
    }

    private String krypterPassord(String passord){
        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder(15);

        return bCrypt.encode(passord);
    }

    public boolean krypterAllePassord(){
        String sql = "SELECT * FROM Bruker";
        String kryptertPassord;

        try {
            List<Bruker> list = db.query(sql, new BeanPropertyRowMapper(Bruker.class));

            for (Bruker bruker : list) {
                kryptertPassord = krypterPassord(bruker.getPassord());

                sql = "UPDATE Bruker SET passord=? WHERE id=?";
                db.update(sql, kryptertPassord, bruker.getId());
            }
            return true;
        } catch (Exception e){
            logger.error("Klarte ikke å oppdatere alle brukere");
            return false;
        }
    }


















}
