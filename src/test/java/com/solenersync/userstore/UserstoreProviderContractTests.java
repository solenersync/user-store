package com.solenersync.userstore;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.*;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactBrokerAuth;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import com.solenersync.userstore.respository.UserRepository;
import com.solenersync.userstore.service.UserService;
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
@PactBroker(url = "https://solenersync.pactflow.io", authentication = @PactBrokerAuth(token = "${PACT_BROKER_TOKEN}"))
//@PactFolder("pacts")
@IgnoreMissingStateChange
@ExtendWith(SpringExtension.class)
@ActiveProfiles("pact-provider")
public class UserstoreProviderContractTests {

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

    @State("a user exists")
    void getUserByEmail() {
        StubSetup.stubForGetUserByEmail(userRepository);
    }

    @State("a user doesnt exist")
    void getUserByEmailFail() {
        StubSetup.stubForGetUserByEmailFail(userRepository);
    }

    @State("a user is created")
    void createUser() {
        StubSetup.stubForCreateUser(userRepository);
    }

    @State("a user fails to be created")
    void createUserFail() {
        StubSetup.stubForCreateUserFail(userRepository);
    }

    @State("a user is updated")
    void updateUser() {
        StubSetup.stubForUpdateUser(userRepository);
    }

    @State("a user fails to be updated")
    void updateUserFail() {
        StubSetup.stubForUpdateUser(userRepository);
    }

    @State("user credentials match")
    void authenticateUser() {
        StubSetup.stubForAuthenticateUser(userRepository);
    }

    @State("user credentials dont match")
    void authenticateUserFail() {
        StubSetup.stubForAuthenticateUser(userRepository);
    }
}
