import com.favoritemedium.partytime.domain.*


class TribeServiceTests extends GroovyTestCase {
    
    Tribe tribe
    User creator
    def tribeService

    void setUp() {
        creator =  User.findByUsername("tester") ?: new User(username:'tester', password:'123456', email:'test@123.com')
        creator.save(flush:true)
        creator.errors.allErrors.each { println it }
    }

    void testCreateNewTribe() {

        def struct = ["mantra":"We test Grails"]

        tribe = tribeService.createNewTribe(creator, "Test Tribe", "Tester Club", false, struct)
        assertNotNull "Tribe should already be created", tribe

        assertEquals "Mantra should have already been set", 
                        tribe.getAttribute('mantra'), "We test Grails"
    }

    void testChangeAttributes() {
        def struct = ["mantra":"Grails Testing Power"]

        tribe = tribeService.createNewTribe(creator, "Test Tribe", "Tester Club", false, struct)
        assertNotNull "Tribe should already be created", tribe

        assertEquals "Mantra should have already been set", 
                        tribe.getAttribute('mantra'), struct['mantra']

        def newStruct = ["mantra":"TEST TEST TEST"]
        tribeService.updateTribeAttribute(tribe, newStruct)
        
        assertEquals "Mantra should have been changed", 
                        tribe.getAttribute('mantra'), newStruct['mantra']

    }

    void testAddMemberToTribe() {
        def member = User.findByUsername("member1") ?: new User(username:"member1", password:"123456")
        member.save(flush:true)

        def struct = ["mantra":"We test Grails"]
        tribe = tribeService.createNewTribe(creator, "Test Tribe", "Tester Club", false, struct)
        assertNotNull "Tribe should already be created", tribe

        assertTrue "Should have added member to tribe",
                        tribeService.addMemberToTribe(tribe, member)
        assertEquals "Member number should be 1", 1, tribe.members?.size()
    }
    
    void testRemoveMemberFromTribe() {
        def member = User.findByUsername("member1") ?: new User(username:"member1", password:"123456")
        member.save(flush:true)

        def struct = ["mantra":"We test Grails"]
        tribe = tribeService.createNewTribe(creator, "Test Tribe", "Tester Club", false, struct)
        assertNotNull "Tribe should already be created", tribe

        assertTrue "Should have added member to tribe",
                        tribeService.addMemberToTribe(tribe, member)
        assertEquals "Member number should be 1", 1, tribe.members?.size()


        assertTrue "Should have removed member from tribe",
                        tribeService.removeMemberFromTribe(tribe, member)
        assertEquals "Member number should now be 0", 0, tribe.members?.size()
    }

    void testTribesForMember() {
        def struct = ["mantra":"We test Grails"]
        tribe = tribeService.createNewTribe(creator, "Original Tribe", "Original Club", false, struct)
        def tribe1 = tribeService.createNewTribe(creator, "Test Tribe", "Tester Club", false, struct)
        def tribe2 = tribeService.createNewTribe(creator, "Dev Tribe", "Dev Club", false, struct)

        assertEquals "Should have created 2 tribes now", Tribe.count(), 3

        def tribes = tribeService.tribesForMember(creator)

        assertEquals "Should get back 3 tribes for the creator", tribes?.size(), 3
        
        def member = User.findByUsername("member1") ?: new User(username:"member1", password:"123456")
        member.save(flush:true)

        tribeService.addMemberToTribe(tribe1, member)

        def member1Tribe = tribeService.tribesForMember(member)
        assertEquals "Should only be 1", 1, member1Tribe?.size()
        
        assertEquals "Tribe member should only be 1", 1, member1Tribe?.members?.size()

        def m = member1Tribe.members?.toArray()[0]
        assertEquals "Should be part of the tribe member already", m?.id, [member?.id]

    }
    
    void testAddTribeMessage() {
        def struct = ["mantra":"We test Grails"]
        tribe = tribeService.createNewTribe(creator, "Test Tribe", "Tester Club", false, struct)
        assertNotNull "Tribe should already be created", tribe

        assertTrue tribeService.addMessage(tribe, creator, "Hi all", "Welcome to Test Tribe")
        assertEquals "Should be a new tribe message now",  1, TribeMessage.count()
    }

    void testLatestMessage() {
        
        def struct = ["mantra":"We test Grails"]
        tribe = tribeService.createNewTribe(creator, "Test Tribe", "Tester Club", false, struct)
        assertNotNull "Tribe should already be created", tribe
        
        assertEquals "Should be empty intially",
                        0, tribeService.getLatestMessage(tribe)?.size()


        tribeService.addMessage(tribe, creator, "More test", "More test to come")
        
        assertEquals "Now should be 1",
                        1, tribeService.getLatestMessage(tribe)?.size()
        
        tribeService.addMessage(tribe, creator, "One More test", "Another one more test to come")
        
        assertEquals "Now should be 1",
                        2, tribeService.getLatestMessage(tribe)?.size()
    }


    void testSendMessageToTribeMember() {
        def struct = ["mantra":"We test Grails"]
        tribe = tribeService.createNewTribe(creator, "Test Tribe", "Tester Club", false, struct)
        assertNotNull "Tribe should already be created", tribe

        def member = User.findByUsername("member1") ?: new User(username:"member1", password:"123456")
        member.save(flush:true)

        tribeService.addMemberToTribe(tribe, member)

        assertEquals "Should only be 1", 1, tribe.members?.size()
        
        tribeService.sendMessageToTribeMember(tribe, "Hi there", "This is a test tribe message to everyone in the tribe")

        assertEquals "Message count have 2 messages now",
                1, Message.count()

        def msg = Message.list()[0]
        assertTrue "This message should be from the tribe creator",
                        msg.from == creator
        assertTrue "This message should be to the member", 
                        msg.to == member

    }

    void testDeleteTribeMessage() {
        def struct = ["mantra":"We test Grails"]
        tribe = tribeService.createNewTribe(creator, "Test Tribe", "Tester Club", false, struct)
        assertNotNull "Tribe should already be created", tribe
        
        assertEquals "Should be empty intially",
                        0, tribeService.getLatestMessage(tribe)?.size()


        tribeService.addMessage(tribe, creator, "More test", "More test to come")
        
        assertEquals "Now should be 1",
                        1, tribeService.getLatestMessage(tribe)?.size()
        
        tribeService.addMessage(tribe, creator, "One More test", "Another one more test to come")
        
        assertEquals "Now should be 1",
                        2, tribeService.getLatestMessage(tribe)?.size()
        def msg1 = TribeMessage.list()[0]
        def msg2 = TribeMessage.list()[1]

        tribeService.deleteMessage(msg1)
        assertEquals "Now should be 1", 1, tribeService.getLatestMessage(tribe)?.size()
        
        tribeService.deleteMessage(msg2)
        assertEquals "Now should be 0", 0, tribeService.getLatestMessage(tribe)?.size()
    }

    void tearDown() {

        Tribe.list().each { t ->
            t?.delete(flush:true)
        }

        TribeMessage.list().each {
            it.delete(flush:true)
        }
        
        User.list().each {
            it.delete(flush:true)
        }
        
        Message.list().each {
            it.delete(flush:true)
        }

    }

}

