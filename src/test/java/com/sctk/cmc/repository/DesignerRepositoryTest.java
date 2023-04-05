package com.sctk.cmc.repository;

import com.sctk.cmc.domain.Designer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class DesignerRepositoryTest {
    @Autowired
    DesignerRepository designerRepository;

    @Test
    public void designer_저장_테스트() throws Exception {
        Designer designer = createDesigner();

        Long returned = designerRepository.save(designer);

        assertThat(designer.getId()).isEqualTo(returned);
    }

    @Test
    public void designer_name으로_조회() throws Exception {
        //given
        String name = "designer";
        String email = "email";
        Designer designer = createDesignerWithNameEmail(name, email);
        designerRepository.save(designer);

        //when
        List<Designer> findDesigners = designerRepository.findAllByName(name);

        //then
        assertThat(findDesigners.size()).isGreaterThan(0);
        assertThat(findDesigners).contains(designer);
    }

    @Test
    public void designer_name으로_조회_중복있을때() throws Exception {
        //given
        String name = "designer";
        String emailA = "emailA";
        String emailB = "emailB";
        Designer designerA = createDesignerWithNameEmail(name, emailA);
        designerRepository.save(designerA);

        Designer designerB = createDesignerWithNameEmail(name, emailB);
        designerRepository.save(designerB);

        //when
        List<Designer> findDesigners = designerRepository.findAllByName(name);

        //then
        assertThat(findDesigners.size()).isEqualTo(2);
        assertThat(findDesigners).contains(designerA);
        assertThat(findDesigners).contains(designerB);
    }

    @Test
    public void designer_name으로_조회_결과_없을때() throws Exception {
        //given
        String name = "designer";

        //when
        List<Designer> findDesigners = designerRepository.findAllByName(name);

        //then
        assertThat(findDesigners.size()).isEqualTo(0);
        assertThat(findDesigners).isEmpty();
    }

    @Test
    public void designer_email로_조회() throws Exception {
        //given
        String name = "designer";
        String email = "email";
        Designer designer = createDesignerWithNameEmail(name, email);
        designerRepository.save(designer);

        //when
        Designer findDesigner = designerRepository.findByEmail(email)
                .orElse(null);

        //then
        assertThat(findDesigner).isNotNull();
        assertThat(findDesigner.getId().equals(designer.getId())).isTrue();
        assertThat(findDesigner.getEmail().equals(designer.getEmail())).isTrue();
    }

    public Designer createDesigner() {
        return Designer.builder()
                .build();
    }

    public Designer createDesignerWithNameEmail(String name, String email) {
        return Designer.builder()
                .name(name)
                .email(email)
                .build();
    }
}