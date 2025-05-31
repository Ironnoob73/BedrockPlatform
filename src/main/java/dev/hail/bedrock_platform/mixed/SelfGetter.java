package dev.hail.bedrock_platform.mixed;

@SuppressWarnings("unchecked")
public interface SelfGetter<T> {
    default T bedrockPlatform$self(){
        return (T) this;
    }
}
