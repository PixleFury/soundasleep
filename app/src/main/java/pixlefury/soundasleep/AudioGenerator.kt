package pixlefury.soundasleep

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import noise.FastNoiseLite

class AudioGenerator(var noise: FastNoiseLite) {
	private var playing = false

	private lateinit var buffer: FloatArray
	private lateinit var track: AudioTrack
	private lateinit var audioThread: Thread

	var volume = 0.4f
	private var duration: Long = 0

	fun play(millis: Long = 0) {
		if (playing) {
			return
		}

		duration = millis

		playing = true

		val sampleRate = 44100
		val channelConfig = AudioFormat.CHANNEL_OUT_MONO
		val encoding = AudioFormat.ENCODING_PCM_FLOAT

		track = AudioTrack.Builder()
			.setAudioAttributes(
				AudioAttributes.Builder()
					.setUsage(AudioAttributes.USAGE_MEDIA)
					.setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
					.build())
			.setAudioFormat(
				AudioFormat.Builder()
					.setEncoding(encoding)
					.setSampleRate(sampleRate)
					.setChannelMask(channelConfig)
					.build())
			.setBufferSizeInBytes(AudioTrack.getMinBufferSize(sampleRate, channelConfig, encoding))
			.setTransferMode(AudioTrack.MODE_STREAM)
			.build()

		buffer = FloatArray(track.bufferCapacityInFrames)

		audioThread = Thread {
			var time = 0f

			while (playing) {
				if (duration != 0L && time >= duration) {
					break
				}

				for (i in buffer.indices) {
					buffer[i] = sample(time) * volume
					time += 1f
				}
				track.write(buffer, 0, buffer.size, AudioTrack.WRITE_BLOCKING)
				Thread.sleep(1)
			}
		}

		audioThread.start()
		track.play()

	}

	fun stop() {
		playing = false
		track.pause()
		track.flush()
		track.release()
	}

	fun isPlaying(): Boolean = playing

    private fun sample(time: Float): Float {
        return noise.GetNoise(time, 0.0f)
    }
}