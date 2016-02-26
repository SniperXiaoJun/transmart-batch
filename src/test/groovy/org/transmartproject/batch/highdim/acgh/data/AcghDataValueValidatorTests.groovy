package org.transmartproject.batch.highdim.acgh.data

import org.junit.Before
import org.junit.Test
import org.springframework.validation.BeanPropertyBindingResult
import org.springframework.validation.Errors
import org.springframework.validation.ObjectError

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.*

/**
 * Test... {@link org.transmartproject.batch.highdim.acgh.data.AcghDataValueValidator}!
 */
class AcghDataValueValidatorTests {

    AcghDataValueValidator testee
    AcghDataValue acghDataValue
    Errors errors

    @Before
    void setUp() {
        testee = new AcghDataValueValidator()
        acghDataValue = new AcghDataValue(
                regionName: 'region-name',
                sampleCode: 'sample-code',
                flag: 0,
                chip: 0.1d,
                segmented: 0.2d,

                probHomLoss: 0.1d,
                probLoss: 0.2d,
                probNorm: 0.4d,
                probGain: 0.2d,
                probAmp: 0.1d
        )
        errors = new BeanPropertyBindingResult(acghDataValue, "item")
    }

    @Test
    void testValid() {
        assertThat callValidate().hasErrors(), is(false)
    }

    @Test
    void testRegionNameIsNull() {
        acghDataValue.regionName = null

        List<ObjectError> errors = callValidate().allErrors

        assertThat errors, contains(allOf(
                hasProperty('field', equalTo('regionName')),
                hasProperty('code', equalTo('required'))
        ))
    }

    @Test
    void testRegionNameIsEmptyString() {
        acghDataValue.regionName = ''

        List<ObjectError> errors = callValidate().allErrors

        assertThat errors, contains(allOf(
                hasProperty('field', equalTo('regionName')),
                hasProperty('code', equalTo('required'))
        ))
    }

    @Test
    void testRegionNameIsBlank() {
        acghDataValue.regionName = ' '

        List<ObjectError> errors = callValidate().allErrors

        assertThat errors, contains(allOf(
                hasProperty('field', equalTo('regionName')),
                hasProperty('code', equalTo('required'))
        ))
    }

    @Test
    void testSampleCodeIsNull() {
        acghDataValue.sampleCode = null

        List<ObjectError> errors = callValidate().allErrors

        assertThat errors, contains(allOf(
                hasProperty('field', equalTo('sampleCode')),
                hasProperty('code', equalTo('required'))
        ))
    }

    @Test
    void testSampleCodeIsEmptyString() {
        acghDataValue.sampleCode = ''

        List<ObjectError> errors = callValidate().allErrors

        assertThat errors, contains(allOf(
                hasProperty('field', equalTo('sampleCode')),
                hasProperty('code', equalTo('required'))
        ))
    }

    @Test
    void testSampleCodeIsBlank() {
        acghDataValue.sampleCode = ' '

        List<ObjectError> errors = callValidate().allErrors

        assertThat errors, contains(allOf(
                hasProperty('field', equalTo('sampleCode')),
                hasProperty('code', equalTo('required'))
        ))
    }

    @Test
    void testFlagHasNotAllowedValue() {
        acghDataValue.flag = -3

        List<ObjectError> errors = callValidate().allErrors

        assertThat errors, contains(allOf(
                hasProperty('field', equalTo('flag')),
                hasProperty('code', equalTo('notAllowedValue'))
        ))
    }

    @Test
    void testProbabilitiesDoNotSumUpToOne() {
        acghDataValue.probHomLoss = 1
        acghDataValue.probGain = 0.5

        List<ObjectError> errors = callValidate().allErrors

        assertThat errors, contains(
                hasProperty('code', equalTo('sumOfProbabilitiesIsNotOne'))
        )
    }

    private Errors callValidate() {
        testee.validate(acghDataValue, errors)
        errors
    }
}
