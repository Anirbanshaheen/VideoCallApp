# VideoCallApp 
# author - Shaheen

Branches ->

master - Here someone can stream a video as a host and other users can view that video as audience.

VideoCall - Here two user can join in the same channel and have a private video communication.
            setback is each time I have to create a unique channel for two users to join. I couldn't make this channel creation dynamic.

VideoCallWithChat - with private video chat here two user can have peer to peer messaging between them.
                    setback is each time I have to generate a token for a user when token is expired. I researched about agora token generation and found that
                    it needs backend support to generate new tokens for users.
