package com.solenersync.userstore;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.*;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import com.solenersync.userstore.respository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Provider("user-store")
@Consumer("ses-front-end")
@PactBroker(url = "https://solenersync.pactflow.io")
@IgnoreNoPactsToVerify
@IgnoreMissingStateChange
@ExtendWith(SpringExtension.class)
@ActiveProfiles("pact-provider")
public class UserstoreProviderTests {

    @MockBean
    UserRepository userRepository;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup(PactVerificationContext context) {
        if (context != null) {
            context.setTarget(new HttpTestTarget("localhost", port));
        }
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        if (context != null) {
            context.verifyInteraction();
        }
    }

    @State("should return user details")
    void getUserByEmail() {
        StubSetup.stubForGetUserByEmail(userRepository);
    }

    @State("should create user and return user details")
    void createUser() {
        StubSetup.stubForCreateUser(userRepository);
    }

    @State("should update user and return status 200")
    void updateUser() {
        StubSetup.stubForUpdateUser(userRepository);
    }
}
